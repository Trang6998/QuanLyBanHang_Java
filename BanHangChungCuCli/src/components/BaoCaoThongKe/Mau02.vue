<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12><h2>Bảng kê mua bán hàng hóa của nhà cung cấp</h2></v-flex>
            <v-flex xs12 md3>
                <v-datepicker label="Từ ngày" hide-details v-model="searchParamsChiTietDonDatHang.tuNgay"
                              :max="searchParamsChiTietDonDatHang.denNgay"
                              @input="getDataFromApi(searchParamsChiTietDonDatHang)"></v-datepicker>
            </v-flex>
            <v-flex xs12 md3>
                <v-datepicker label="Đến ngày" hide-details v-model="searchParamsChiTietDonDatHang.denNgay"
                              :min="searchParamsChiTietDonDatHang.tuNgay"
                              @input="getDataFromApi(searchParamsChiTietDonDatHang)"></v-datepicker>
            </v-flex>
            <v-flex xs12 sm3>
                <v-select :items="dsToaNha"
                          placeholder="Chọn chung cư"
                          label="Chọn chung cư"
                          v-model="searchParamsChiTietDonDatHang.toaNhaID"
                          @change="getDataFromApi(searchParamsChiTietDonDatHang)"
                          item-value="ToaNhaId"
                          item-text="TenToaNha"
                          hide-details
                          autocomplete></v-select>
            </v-flex>
            <v-flex xs12 sm3>
                <v-select :items="dsNhaCungCap"
                          placeholder="Chọn nhà cung cấp"
                          label="Chọn nhà cung cấp"
                          v-model="searchParamsChiTietDonDatHang.nhaCungCapID"
                          @change="getDataFromApi(searchParamsChiTietDonDatHang)"
                          item-value="nhaCungCapID"
                          item-text="tenNhaCungCap"
                          hide-details
                          autocomplete></v-select>
            </v-flex>
            <v-flex xs12>
                <v-radio-group hide-details v-model="searchParamsChiTietDonDatHang.tinhTrang" @change="getDataFromApi(searchParamsChiTietDonDatHang)" row>
                    <v-radio label="Tất cả" :value="-1"></v-radio>
                    <v-radio label="Chưa nhận đơn" :value="1"></v-radio>
                    <v-radio label="Đang xử lý" :value="2"></v-radio>
                    <v-radio label="Hoàn thành" :value="4"></v-radio>
                    <v-radio label="Khách chưa thanh toán" :value="3"></v-radio>
                </v-radio-group>
            </v-flex>
            <v-flex xs12>
                <v-layout row wrap style="padding-right: 5px">
                    <v-spacer></v-spacer>
                    <v-btn small color="primary" :href="exportFile()" target="_blank" class="ma-0">
                        Tải xuống mẫu 02 (Gửi tới NCC)
                    </v-btn>
                    &emsp;
                    <v-btn small color="primary" :href="exportFile1()" target="_blank" class="ma-0">
                        Tải xuống mẫu 04 (Nhận từ NCC)
                    </v-btn>
                </v-layout>
            </v-flex>
            <v-flex xs12>
                <v-data-table :headers="tableHeader"
                              :items="dsDonDatHang"
                              :pagination.sync="searchParamsChiTietDonDatHang"
                              hide-actions
                              :loading="loadingTable"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                        <td class="text-xs-center"> {{props.index + 1}} </td>
                        <td>
                            {{ props.item.tenSanPham }}
                        </td>
                        <td>{{ props.item.ngayDat === null ? "" : props.item.ngayDat|moment('HH:mm DD/MM/YY') }}</td>
                        <td>
                            <span style="white-space: nowrap">{{ props.item.tenHoGiaDinh }}</span>
                            <br />
                            <small style="white-space: nowrap">
                                Phòng: {{ props.item.tenPhong }}
                            </small>
                        </td>
                        <td>
                            {{ props.item.soHieuDon }}
                        </td>

                        <td>
                            {{ props.item.donViTinh }}
                        </td>
                        <td>
                            {{ props.item.soLuong }}
                        </td>
                      
                        <td>{{ props.item.trangThaiDon }}</td>
                        <td>{{ props.item.ghiChu }}</td>
                    </template>
                    <template slot="no-data">
                        <p class="text-xs-center mb-0">Không có dữ liệu</p>
                    </template>
                </v-data-table>
                <div class="text-xs-center pt-2 xxx">
                    <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsChiTietDonDatHang)" v-model="page" :length="searchParamsChiTietDonDatHang.totalPages"></v-pagination>
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
import NhaCungCapApi, { NhaCungCapApiSearchParams } from '@/apiResources/NhaCungCapApi';
import { NhaCungCap } from '@/models/NhaCungCap';
import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    export default Vue.extend({
        components: {},
        data() {
            return {
                APIS: APIS,
                dsDonDatHang: [] as ChiTietDonDatHang[],
                dsNhaCungCap: [] as NhaCungCap[],
                tableHeader: [
                    { text: 'STT', value: '#', align: 'center', sortable: false },
                    { text: 'Tên hàng hóa', value: 'soHieuDon', align: 'center', sortable: false },
                    { text: 'Thời gian đặt', value: '#', align: 'center', sortable: false },
                    { text: 'Tên, địa chỉ khách hàng', value: 'ngayDat', align: 'center', sortable: false },
                    { text: 'Số đơn hàng', value: 'soHieuDon', align: 'center', sortable: false },
                    { text: 'Đơn vị tính', value: '#', align: 'center', sortable: false },
                    { text: 'Số lượng', value: '#', align: 'center', sortable: false },
                    { text: 'Trạng thái đơn', value: '#', align: 'center', sortable: false },
                    { text: 'Ghi chú', value: '#', align: 'center', sortable: false },
                ],
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 10, tinhTrang: -1 } as ChiTietDonDatHangApiSearchParams,
                searchParamsNhaCungCap: { includeEntities: true, rowsPerPage: 0 } as NhaCungCapApiSearchParams,
                loadingTable: false,
                sanPham: {} as SanPham,
                dsToaNha: [] as any,
                page: 1,
            }
        },
        watch: {
            page: function () {
                this.searchParamsChiTietDonDatHang.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsChiTietDonDatHang.rowsPerPage == null ||
                    this.searchParamsChiTietDonDatHang.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsChiTietDonDatHang.totalItems / this.searchParamsChiTietDonDatHang.rowsPerPage);
            }
        },
        created() {
            this.searchParamsChiTietDonDatHang.tuNgay = this.$moment().startOf('month');
            this.searchParamsChiTietDonDatHang.denNgay = new Date();
            this.getDanhSachToaNha();
            this.getDsNhaCungCap();
        },
        methods: {
            getDataFromApi(searchParamsChiTietDonDatHang: DonDatHangApiSearchParams): void {
                this.loadingTable = true;
                ChiTietDonDatHangApi.search(searchParamsChiTietDonDatHang).then(res => {
                    this.dsDonDatHang = res.data;
                    this.searchParamsChiTietDonDatHang.totalItems = res.pagination.totalItems;
                    this.searchParamsChiTietDonDatHang.page = (res.pagination.page as any) + 1;
                    this.searchParamsChiTietDonDatHang.totalPages = res.pagination.totalPages;
                    this.loadingTable = false;
                });
            },
            exportFile() {
                return APIS.HOST + 'Export/ExportMau02?tuNgay=' + this.searchParamsChiTietDonDatHang.tuNgay +
                    '&denNgay=' + this.searchParamsChiTietDonDatHang.denNgay +
                    '&toaNhaID=' + this.searchParamsChiTietDonDatHang.toaNhaID +
                    '&tinhTrang=' + this.searchParamsChiTietDonDatHang.tinhTrang +
                    '&nhaCungCapID=' + this.searchParamsChiTietDonDatHang.nhaCungCapID;
            },
            exportFile1() {
                return APIS.HOST + 'Export/ExportMau04?tuNgay=' + this.searchParamsChiTietDonDatHang.tuNgay +
                    '&denNgay=' + this.searchParamsChiTietDonDatHang.denNgay +
                    '&toaNhaID=' + this.searchParamsChiTietDonDatHang.toaNhaID +
                    '&tinhTrang=' + this.searchParamsChiTietDonDatHang.tinhTrang +
                    '&nhaCungCapID=' + this.searchParamsChiTietDonDatHang.nhaCungCapID;
            },
            getDanhSachToaNha() {
                HTTP.get('odata/ToaNha').then(res => {
                    this.dsToaNha.push({
                        ToaNhaId: null as any,
                        TenToaNha: 'Tất cả'
                    } as any);
                    this.dsToaNha.push(...res.data.value);
                    this.searchParamsChiTietDonDatHang.toaNhaID = null as any;
                })
            },
            getDsNhaCungCap() {
                NhaCungCapApi.search(this.searchParamsNhaCungCap).then(res => {
                    this.dsNhaCungCap = res.data;
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