<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12><h3>Danh sách đơn đặt hàng</h3></v-flex>
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
            <v-flex xs12 sm2>
                <v-layout nowrap>
                    <v-spacer></v-spacer>
                    <v-btn small @click="showModalTongHopDon" style="margin-top: 20px" color="primary">Tổng hợp đơn</v-btn>
                </v-layout>
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
                        Tải xuống
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
                            <a @click="showCapNhatDonHang(props.item)"> {{ props.item.soHieuDon }}</a>
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
                        <td class="text-xs-center text-nowrap" style="width:100px; white-space: nowrap">
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on }">
                                    <v-btn icon flat small color="primary" v-on="on" class="ma-0" @click="nhanDon(props.item)" v-if="props.item.tinhTrang == 1">
                                        <v-icon small>assignment_returned</v-icon>
                                    </v-btn>
                                </template>
                                <span>Xác nhận đơn</span>
                            </v-tooltip>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on }">
                                    <v-btn icon flat small v-on="on" color="dark green" @click="showCapNhatDonHang(props.item)" class="ma-0">
                                        <v-icon small>edit</v-icon>
                                    </v-btn>
                                </template>
                                <span>Cập nhật đơn</span>
                            </v-tooltip>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on }">
                                    <v-btn icon flat small v-on="on" color="red" @click="showCapNhatDonHang(props.item)" class="ma-0">
                                        <v-icon small>delete</v-icon>
                                    </v-btn>
                                </template>
                                <span>Xóa đơn</span>
                            </v-tooltip>
                        </td>
                    </template>
                    <template slot="no-data">
                        <p class="text-xs-center mb-0">Không có dữ liệu</p>
                    </template>
                </v-data-table>
                <div class="text-xs-center pt-2 xxx">
                    <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsDonDatHang)" v-model="page" :length="searchParamsDonDatHang.totalPages"></v-pagination>
                </div>
            </v-flex>
            <tong-hop-don ref="tongHopDon"></tong-hop-don>
            <cap-nhat-don-hang @getData="getDataFromApi(searchParamsDonDatHang)" ref="capNhatDonHang"></cap-nhat-don-hang>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteDonDatHang" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
        
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { DonDatHang } from '@/models/DonDatHang';
    import { HTTP } from '@/http-servicesNew';
    import CapNhatDonHang from './CapNhatDonHang.vue'
    import TongHopDon from './TongHopDon.vue'
    import APIS from '@/apisServer';
    export default Vue.extend({
        components: { TongHopDon, CapNhatDonHang },
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
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 10, tinhTrang: -1 } as DonDatHangApiSearchParams,
                loadingTable: false,
                selectedDonDatHang: {} as DonDatHang,
                dialogConfirmDelete: false,
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
            nhanDon(donHang: DonDatHang) {
                donHang.tinhTrang = 2 // đã nhận đơn - đang xử lý
                DonDatHangApi.update(donHang.donDatHangID, donHang).then(res => {
                    this.getDataFromApi(this.searchParamsDonDatHang);
                    this.$eventBus.$emit('UpdateSoDonChuaNhan', 0);
                    this.$snotify.success('Nhận đơn số ' + donHang.soHieuDon + ' thành công!');
                }).catch(res => {
                    this.$snotify.error('Nhận đơn thất bại!');
                });
            },
            exportFile() {
                return APIS.HOST + 'Export/ExportXuLyDonHang?tuNgay=' + this.searchParamsDonDatHang.tuNgay +
                    '&denNgay=' + this.searchParamsDonDatHang.denNgay +
                    '&toaNhaID=' + this.searchParamsDonDatHang.toaNhaID +
                    '&tinhTrang=' + this.searchParamsDonDatHang.tinhTrang;
            },
            showModalViewBaoCao() {

            },
            showCapNhatDonHang(donHang: DonDatHang) {
                (this.$refs.capNhatDonHang as any).show(donHang.donDatHangID);
            },
            showModalTongHopDon() {
                (this.$refs.tongHopDon as any).show(this.searchParamsDonDatHang.tuNgay, this.searchParamsDonDatHang.denNgay, this.searchParamsDonDatHang.tinhTrang)
            },
            confirmDelete(datDichVu: DonDatHang): void {
                this.selectedDonDatHang = datDichVu;
                this.dialogConfirmDelete = true;
            },
            deleteDonDatHang(): void {
                DonDatHangApi.delete(this.selectedDonDatHang.donDatHangID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDonDatHang);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
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