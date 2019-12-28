<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12><h2>Bảng kê mua bán hàng hóa</h2></v-flex>
            <v-flex xs12 md3>
                <v-datepicker label="Từ ngày" hide-details v-model="searchParamsDonDatHang.tuNgay"
                                  :max="searchParamsDonDatHang.denNgay"
                              @input="getDataFromApi(searchParamsDonDatHang)"></v-datepicker>
            </v-flex>
            <v-flex xs12 md3>
                <v-datepicker label="Đến ngày" hide-details v-model="searchParamsDonDatHang.denNgay"
                                  :min="searchParamsDonDatHang.tuNgay"
                               @input="getDataFromApi(searchParamsDonDatHang)"></v-datepicker>
            </v-flex>
            <v-flex xs12 sm3>
                <v-select :items="dsToaNha"
                          placeholder="Chọn chung cư"
                          label="Chọn chung cư"
                          v-model="searchParamsDonDatHang.toaNhaID"
                          @change="getDsSanPham"
                          item-value="ToaNhaId"
                          item-text="TenToaNha"
                          hide-details
                          autocomplete></v-select>
            </v-flex>
            <v-flex xs12 sm3>
                <v-select :items="dsSanPham"
                          placeholder="Chọn sản phẩm"
                          label="Chọn sản phẩm"
                          v-model="searchParamsDonDatHang.sanPhamID"
                          @change="getDataFromApi(searchParamsDonDatHang)"
                          item-value="sanPhamID"
                          item-text="tenSanPham"
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
                            {{ props.item.soHieuDon }}
                        </td>
                        <td>{{ props.item.ngayDat === null ? "" : props.item.ngayDat|moment('HH:mm DD/MM/YY') }}</td>
                        <td>
                            <span style="white-space: nowrap">{{ props.item.tenKhachHang }}</span>
                            <br />
                            <small style="white-space: nowrap">
                                Phòng: {{ props.item.tenPhong }}
                            </small>
                        </td>
                        <td>
                            {{ props.item.chiTietDon ? props.item.chiTietDon.donViTinh : "" }}
                        </td>
                        <td>
                            {{ props.item.chiTietDon ? props.item.chiTietDon.soLuong : "" }}
                        </td>
                        <td>
                            {{ props.item.chiTietDon ? (props.item.chiTietDon.giaXuat? props.item.chiTietDon.giaXuat: "") : "" | currency('', 0,{ thousandsSeparator: '.' }) }}
                        </td>
                        <td>
                            {{ props.item.chiTietDon ? (props.item.chiTietDon.thanhTien ? props.item.chiTietDon.thanhTien : "") : "" | currency('', 0,{ thousandsSeparator: '.' }) }}
                        </td>
                        <td>{{ props.item.trangThaiDon }}</td>
                        <td>{{ props.item.ghiChu }}</td>
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
    import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';
import { SanPham } from '@/models/SanPham';
    export default Vue.extend({
        components: {},
        data() {
            return {
                APIS: APIS,
                dsDonDatHang: [] as DonDatHang[],
                dsSanPham: [] as SanPham[],
                tableHeader: [
                    { text: 'Số đơn hàng', value: 'soHieuDon', align: 'center', sortable: false },
                    { text: 'Thời gian đặt', value: '#', align: 'center', sortable: false },
                    { text: 'Tên, địa chỉ khách hàng', value: 'ngayDat', align: 'center', sortable: false },
                    { text: 'Đơn vị tính', value: '#', align: 'center', sortable: false },
                    { text: 'Số lượng', value: '#', align: 'center', sortable: false },
                    { text: 'Giá bán', value: '#', align: 'center', sortable: false },
                    { text: 'Thành tiền bán', value: '#', align: 'center', sortable: false },
                    { text: 'Trạng thái đơn', value: '#', align: 'center', sortable: false },
                    { text: 'Ghi chú', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 10, tinhTrang: -1 } as DonDatHangApiSearchParams,
                searchParamsSanPham: { includeEntities: true, rowsPerPage: 0 } as SanPhamApiSearchParams,
                loadingTable: false,
                sanPham: {} as SanPham,
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
            this.getDanhSachToaNha();
            this.getDsSanPham(null as any);
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
                return APIS.HOST + 'Export/ExportMau03?tuNgay=' + this.searchParamsDonDatHang.tuNgay +
                    '&denNgay=' + this.searchParamsDonDatHang.denNgay +
                    '&toaNhaID=' + this.searchParamsDonDatHang.toaNhaID +
                    '&tinhTrang=' + this.searchParamsDonDatHang.tinhTrang +
                    '&sanPhamID=' + this.searchParamsDonDatHang.sanPhamID;
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
            getDsSanPham(id: number) {
                this.searchParamsSanPham.toaNhaID = id;
                SanPhamApi.search(this.searchParamsSanPham).then(res => {
                    this.dsSanPham = res.data;
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