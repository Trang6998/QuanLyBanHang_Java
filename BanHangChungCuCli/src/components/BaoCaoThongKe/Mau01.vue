<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12><h2>Danh sách chi tiết các đơn hàng</h2></v-flex>
            <v-flex xs12 md3>
                <v-datetimepicker label="Từ ngày" hide-details v-model="searchParamsDonDatHang.tuNgay"
                                  :max="searchParamsDonDatHang.tuNgay"
                                  @input="getDataFromApi(searchParamsDonDatHang)"></v-datetimepicker>
            </v-flex>
            <v-flex xs12 md3>
                <v-datetimepicker label="Đến ngày" hide-details v-model="searchParamsDonDatHang.denNgay"
                                  :min="searchParamsDonDatHang.denNgay" @input="getDataFromApi(searchParamsDonDatHang)"></v-datetimepicker>
            </v-flex>
            <v-flex xs12 sm4>
                <v-select :items="dsToaNha"
                          placeholder="Chọn chung cư"
                          label="Chọn chung cư"
                          v-model="searchParamsDonDatHang.toaNhaID"
                          @change="getDataFromApi(searchParamsDonDatHang)"
                          item-value="ToaNhaId"
                          item-text="TenToaNha"
                          hide-details
                          autocomplete></v-select>
            </v-flex>
            <v-flex xs12>
                <v-radio-group hide-details v-model="searchParamsDonDatHang.tinhTrang" @change="getDataFromApi(searchParamsDonDatHang)" row>
                    <v-radio label="Tất cả" :value="-1"></v-radio>
                    <v-radio label="Chưa nhận đơn" :value="1"></v-radio>
                    <v-radio label="Đang xử lý" :value="2"></v-radio>
                    <v-radio label="Hoàn thành" :value="4"></v-radio>
                    <v-radio label="Khách chưa thanh toán" :value="3"></v-radio>


                </v-radio-group>
            </v-flex>
            <v-flex xs12>
                <v-layout nowrap style="padding-right: 5px">
                    <v-spacer></v-spacer>
                    <v-btn small color="primary" :href="exportFile()" target="_blank" class="ma-0">
                        Tải xuống mẫu 01 (Danh sách để đi đưa hàng)
                    </v-btn>
                    &emsp;
                    <v-btn small color="primary" :href="exportFile1()" target="_blank" class="ma-0">
                        Tải xuống mẫu 05 (Tổng hợp)
                    </v-btn>
                </v-layout>
            </v-flex>
            <v-flex xs12>
                <v-data-table :headers="tableHeader"
                              :items="dsDonDatHang"
                              :pagination.sync="searchParamsDonDatHang"
                              hide-actions
                              :loading="loadingTable"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                        <td class="text-xs-center">
                            {{ props.item.soHieuDon }}
                        </td>
                        <td>
                            <span style="white-space: nowrap">{{ props.item.tenKhachHang }}</span>
                            <br />
                            <small style="white-space: nowrap">
                                Phòng: {{ props.item.tenPhong }}
                            </small>
                        </td>
                        <td>{{ props.item.ngayDat === null ? "" : props.item.ngayDat|moment('HH:mm DD/MM/YY') }}</td>
                        <td>
                            <span v-if="props.item.henLayTu != null && props.item.henLayDen != null">
                                <span v-if="soSanhNgay(props.item.henLayTu, props.item.henLayDen)">
                                    {{ props.item.henLayTu === null ? "" : props.item.henLayTu|moment('HH:mm') }} -
                                    {{ props.item.henLayDen === null ? "" : props.item.henLayDen|moment('HH:mm') }}
                                    {{ props.item.henLayTu === null ? "" : props.item.henLayTu|moment('DD/MM/YY') }}
                                </span>
                                <span v-else>
                                    {{ props.item.henLayTu === null ? "" : props.item.henLayTu|moment('HH:mm DD/MM/YY') }} -
                                    {{ props.item.henLayDen === null ? "" : props.item.henLayDen|moment('HH:mm DD/MM/YY') }}
                                </span>
                            </span>
                            <span v-else-if="props.item.henLayTu != null && props.item.henLayDen == null">
                                Sau
                                {{ props.item.henLayTu === null ? "" : props.item.henLayTu|moment('HH:mm DD/MM/YY') }} -
                            </span>
                            <span v-else-if="props.item.henLayTu == null && props.item.henLayDen != null">
                                Trước
                                {{ props.item.henLayDen === null ? "" : props.item.henLayDen|moment('HH:mm DD/MM/YY') }}
                            </span>
                            <span v-else>
                                Mọi khung giờ
                            </span>
                        </td>
                        <td>
                            {{ props.item.tongTien | currency('', 0,{ thousandsSeparator: '.' }) }}
                        </td>
                        <td>{{ props.item.trangThaiDon }}</td>
                    </template>
                    <template slot="no-data">
                        <p class="text-xs-center mb-0">Không có dữ liệu</p>
                    </template>
                </v-data-table>
                <div class="text-xs-center pt-2 xxx">
                    <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsDonDatHang)" v-model="page" :length="searchParamsDonDatHang.totalPages"></v-pagination>
                </div>
            </v-flex>
            
        </v-layout>

    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { DonDatHang } from '@/models/DonDatHang';
    import { HTTP } from '@/http-servicesNew';
    import APIS from '@/apisServer';
    export default Vue.extend({
        components: { },
        data() {
            return {
                APIS: APIS,
                dsDonDatHang: [] as DonDatHang[],
                tableHeader: [
                    { text: 'Số đơn', value: 'soHieuDon', align: 'center', sortable: false },
                    { text: 'Tên hộ gia đình', value: '#', align: 'center', sortable: false },
                    { text: 'Thời gian đặt', value: 'ngayDat', align: 'center', sortable: false },
                    { text: 'Khung giờ nhận', value: '#', align: 'center', sortable: false },
                    { text: 'Tổng(VNĐ)', value: 'tongTien', align: 'center', sortable: false },
                    { text: 'Trạng thái đơn', value: 'trangThaiDon', align: 'center', sortable: false },
                ],
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 10, tinhTrang: -1 } as DonDatHangApiSearchParams,
                loadingTable: false,
                selectedDonDatHang: {} as DonDatHang,
                dsToaNha: [] as any,
                page: 1,
            }
        },
        watch: {
            page: function () {
                this.searchParamsDonDatHang.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsDonDatHang.rowsPerPage == null ||
                    this.searchParamsDonDatHang.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsDonDatHang.totalItems / this.searchParamsDonDatHang.rowsPerPage);
            }
        },
        created() {
            this.searchParamsDonDatHang.tuNgay = this.$moment().startOf('month');
            this.searchParamsDonDatHang.denNgay = new Date();
            this.getDataFromApi(this.searchParamsDonDatHang);
            this.getDanhSachToaNha();
        },
        methods: {
            getDataFromApi(searchParamsDonDatHang: DonDatHangApiSearchParams): void {
                this.loadingTable = true;
                DonDatHangApi.search(searchParamsDonDatHang).then(res => {
                    this.dsDonDatHang = res.data;
                    this.searchParamsDonDatHang.totalItems = res.pagination.totalItems;
                    this.searchParamsDonDatHang.page = (res.pagination.page as any) + 1;
                    this.searchParamsDonDatHang.totalPages = res.pagination.totalPages;
                    this.loadingTable = false;
                });
            },
            exportFile() {
                return APIS.HOST + 'Export/ExportXuLyDonHang?tuNgay=' + this.searchParamsDonDatHang.tuNgay +
                    '&denNgay=' + this.searchParamsDonDatHang.denNgay +
                    '&toaNhaID=' + this.searchParamsDonDatHang.toaNhaID +
                    '&tinhTrang=' + this.searchParamsDonDatHang.tinhTrang;
            },
            exportFile1() {
                return APIS.HOST + 'Export/ExportMau05?tuNgay=' + this.searchParamsDonDatHang.tuNgay +
                    '&denNgay=' + this.searchParamsDonDatHang.denNgay +
                    '&toaNhaID=' + this.searchParamsDonDatHang.toaNhaID +
                    '&tinhTrang=' + this.searchParamsDonDatHang.tinhTrang;
            },
            getDanhSachToaNha() {
                HTTP.get('odata/ToaNha').then(res => {
                    this.dsToaNha.push({
                        ToaNhaId: null as any,
                        TenToaNha: 'Tất cả'
                    } as any);
                    this.dsToaNha.push(...res.data.value);
                    this.searchParamsDonDatHang.toaNhaID = null as any;
                })
            },
            soSanhNgay(tuNgay: Date, denNgay: Date) {
                if (tuNgay.toString().slice(0, 10) == denNgay.toString().slice(0, 10)) {
                    return true;
                }
                else return false;
            }
        }
    });
</script>

<style>
    .xxx .theme--light.v-pagination .v-pagination__item {
        display: -webkit-box !important;
        background: #fff;
        color: #000;
        width: auto;
        min-width: 34px;
        padding: 0 5px;
    }
</style>