<template>
    <v-flex xs12>
        <!--<v-breadcrumbs divider="/" class="pa-0">
            <v-breadcrumbs-item to="/danh-sach-don-hang" exact>Đơn hàng</v-breadcrumbs-item>
        </v-breadcrumbs>-->
        <v-layout row wrap>
            <v-flex xs12><h3>Danh sách đơn hàng</h3></v-flex>
            <v-flex xs12>
                <v-layout>
                    <v-flex xs12 md3>
                        <v-datepicker label="Từ ngày" hide-details v-model="searchParamsDonDatHang.tuNgay"
                                      :max="searchParamsDonDatHang.tuNgay" @input="getDataFromApi(searchParamsDonDatHang)"
                                      clearable></v-datepicker>
                    </v-flex>
                    <v-flex xs12 md3>
                        <v-datepicker label="Đến ngày" hide-details v-model="searchParamsDonDatHang.denNgay"
                                      :min="searchParamsDonDatHang.denNgay" @input="getDataFromApi(searchParamsDonDatHang)"
                                      clearable></v-datepicker>
                    </v-flex>
                </v-layout>
            </v-flex>
            <v-layout>
                <v-flex xs12 md12>
                    <v-radio-group hide-details v-model="searchParamsDonDatHang.tinhTrang" @change="getDataFromApi(searchParamsDonDatHang)" row>
                        <v-radio label="Tất cả" value="null"></v-radio>
                        <v-radio label="Giỏ hàng" value="0"></v-radio>
                        <v-radio label="Đã đặt hàng" value="1"></v-radio>
                        <v-radio label="Đang xử lý" value="2"></v-radio>
                        <v-radio label="Hoàn thành" value="4"></v-radio>
                        <v-radio label="Chưa thanh toán" value="3"></v-radio>
                    </v-radio-group>
                </v-flex>
            </v-layout>
            <v-flex xs12 sm12 md12 mt-2>
                <v-data-table :headers="headers"
                              :items="dsDonHang" hide-actions 
                              @update:pagination="getDataFromApi" :pagination.sync="searchParamsDonDatHang"
                              :total-items="searchParamsDonDatHang.totalItems"
                              :loading="loadingTable"
                              class="table-border table">
                    <template v-slot:items="props">
                        <td class="text-xs-center">{{ props.index + 1 }}</td>
                        <td class="text-xs-center">{{ props.item.soHieuDon === null ? 'Chưa có' : props.item.soHieuDon }}</td>
                        <td class="text-xs-center">{{ props.item.ngayDat | moment("DD/MM/YYYY hh:mm") }}</td>
                        <td class="text-xs-center">{{ props.item.tongTien | currency('', 0,{ thousandsSeparator: '.' }) }}</td>
                        <td class="text-xs-center">{{ props.item.trangThaiDon }}</td>
                        <td class="text-xs-center">{{ props.item.carbs }}</td>
                        <td class="text-xs-center">
                            <v-btn flat color="primary" small class="ma-0" @click="showChiTietDon(props.item.donDatHangID)">
                                Xem
                            </v-btn>
                            <v-btn v-if="props.item.tinhTrang == 0" flat color="primary" small class="ma-0" @click="confirmMuaDonChon(props.item)">
                                Mua ngay
                            </v-btn>
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
            <v-flex xs12>
                <v-layout nowrap>
                    <div style="font-size: 18px">
                        <b>Tổng tiền:&nbsp;</b>
                    </div>
                    <div style="font-size: 18px; color: red">
                        <b>{{tongTien  | currency('', 0,{ thousandsSeparator: ',' })}}</b>
                    </div>
                </v-layout>
            </v-flex>
            <v-flex xs12 sm12 md12>
                <v-dialog v-model="dialogConfirm" max-width="790">
                    <v-container pa-0 grid-list-md>
                        <v-card>
                            <v-card-title class="primary white--text" style="height: 40px">
                                <h3>Mua hàng </h3>
                                <v-spacer></v-spacer>
                                <v-btn class="white--text ma-0" small @click.native="dialogConfirm=false" icon dark><v-icon>close</v-icon></v-btn>
                            </v-card-title>
                            <v-card-text>
                                <div>Bạn có thể nhận hàng trong khoảng thời gian nào:</div>
                                <v-layout row wrap>
                                    <v-flex xs12>
                                        <v-radio-group v-model="thoigian" row>
                                            <v-radio label="Mọi thời gian" value="moithoigian"></v-radio>
                                            <v-radio label="Theo khung giờ " value="theokhunggio"></v-radio>
                                        </v-radio-group>
                                    </v-flex>
                                    <v-flex xs12 md6 v-if="thoigian == 'theokhunggio'">
                                        <v-datetimepicker v-model="donMuaNgay.henLayTu" label="Từ ngày" :min="new Date()"
                                                          clearable></v-datetimepicker>
                                    </v-flex>
                                    <v-flex xs12 md6 v-if="thoigian == 'theokhunggio'">
                                        <v-datetimepicker v-model="donMuaNgay.henLayDen" label="Đến ngày" :min="thoiGianHenToiThieu"
                                                          clearable></v-datetimepicker>
                                    </v-flex>
                                    <v-flex xs12>
                                        <v-textarea rows="3" v-model="donMuaNgay.ghiChu"
                                                    label="Ghi chú"
                                                    :error-messages="errors.collect('Ghi chú', 'frmAddEdit')"
                                                    v-validate="''"
                                                    data-vv-scope="frmAddEdit"
                                                    data-vv-name="Ghi chú"
                                                    hide-details>
                                        </v-textarea>
                                    </v-flex>
                                </v-layout>
                            </v-card-text>
                            <v-card-actions>
                                <v-layout nowrap>
                                    <v-spacer></v-spacer>
                                    <v-btn class="primary" flat small @click="muaNgay">Xác nhận</v-btn>
                                </v-layout>
                            </v-card-actions>
                        </v-card>
                    </v-container>
                </v-dialog>
            </v-flex>
            <dat-hang-thanh-cong ref="datHangThanhCong"></dat-hang-thanh-cong>
            <chi-tiet-don-hang @getData="getDataFromApi(searchParamsDonDatHang)" ref="chiTietDonHang"></chi-tiet-don-hang>
        </v-layout>
    </v-flex>
</template>
<style>
 v-card-actions {
     padding: 0px;
 }
</style>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { DonDatHang } from '@/models/DonDatHang';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import ChiTietDonHang from './ChiTietDonHang.vue';
    import DatHangThanhCong from '../GioHang/DatHangThanhCong.vue'
    export default Vue.extend({
        components: { ChiTietDonHang, DatHangThanhCong},
        data() {
            return {
                thoigian: 'moithoigian',
                isUpdate: false,
                dschiTietDonDat: [] as ChiTietDonDatHang[],
                dsDonHang: [] as DonDatHang[],
                DonHang: {} as DonDatHang,
                loadingTable: false,
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 10 } as ChiTietDonDatHangApiSearchParams,
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 10 } as DonDatHangApiSearchParams,
                date: new Date().toISOString().substr(0, 10),
                menu1: false,
                modal: false,
                menu2: false,
                headers: [
                    { text: '#', value: '#', align: 'center', sortable: false  },
                    { text: 'Số đơn hàng', value: '#', align: 'center', sortable: false  },
                    { text: 'Ngày đặt', value: '#', align: 'center', sortable: false  },
                    { text: 'Thành tiền', value: '#', align: 'center', sortable: false  },
                    { text: 'Trạng thái', value: '#', align: 'center', sortable: false  },
                    { text: 'Ghi chú', value: '#', align: 'center', sortable: false  },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false  }
                ],
                headers1: [
                    { text: 'Sản phẩm', value: 'calories', align: 'center', sortable: false  },
                    { text: 'Đơn giá', value: 'fat', align: 'center', sortable: false },
                    { text: 'Số lượng', value: 'protein', align: 'center', sortable: false  },
                    { text: 'Thành tiền', value: 'iron', align: 'center', sortable: false  }
                ],
                dialogConfirmDelete: false,
                dialogConfirm: false,
                taiKhoanID: 0,
                donMuaNgay: {} as DonDatHang,
                tongTien: 0,
                thoiGianHenToiThieu: null as any,
                page: 1
            }
        },
        mounted() {
        },
        watch: {
            'donMuaNgay.henLayTu': function (val) {
                this.thoiGianHenToiThieu = this.$moment(val).add(15, 'm');
            },
            page: function () {
                this.searchParamsDonDatHang.page = this.page;
            }
        },
        created() {
            this.searchParamsDonDatHang.tinhTrang = 'null' as any;
            this.getDataFromApi(this.searchParamsDonDatHang);
        },
        methods: {
            getDataFromApi(searchParamsDonDatHang: DonDatHangApiSearchParams): void {
                searchParamsDonDatHang.taiKhoanDatHangID = this.$store.state.user.User.UserId;
                this.loadingTable = true;
                DonDatHangApi.search(searchParamsDonDatHang).then(res => {
                    this.tongTien = 0;
                    this.dsDonHang = res.data;
                    this.searchParamsDonDatHang.page = (res.pagination.page as any) + 1;
                    this.searchParamsDonDatHang.totalPages = res.pagination.totalPages;
                    this.searchParamsDonDatHang.totalItems = res.pagination.totalItems;
                    res.data.forEach(x => {
                        this.tongTien = this.tongTien + (x as any).tongTien
                    })
                    this.loadingTable = false;
                });
            },
            getDataFromApi1(id: number): void {
                ChiTietDonDatHangApi.search({ donDatHangID: id, rowsPerPage: 10 }).then(res => {
                    this.dschiTietDonDat = res.data;
                    this.loadingTable = false;
                });
            },
            getDataFromApi2(id: number): void {
                DonDatHangApi.detail(id).then(res => {
                    this.DonHang = res;
                });
            },
            showChiTietDon(id: number) {
                (this.$refs.chiTietDonHang as any).show(id);
            },
            confirmMuaDonChon(item: DonDatHang) {
                this.dialogConfirm = true;
                this.donMuaNgay = item;
            },
            muaNgay() {
                this.donMuaNgay.ngayDat = this.$moment();
                this.donMuaNgay.tinhTrang = 1;
                this.donMuaNgay.chiTietDonDatHang = undefined;
                DonDatHangApi.update(this.donMuaNgay.donDatHangID, this.donMuaNgay).then(res => {
                    this.$snotify.success('Đặt mua thành công!');
                    this.dialogConfirm = false;
                    this.$eventBus.$emit('UpdateSoDonChuaNhan', 0);
                    (this.$refs.datHangThanhCong as any).show(res as any);
                    this.getDataFromApi(this.searchParamsDonDatHang);
                    this.donMuaNgay = {} as DonDatHang;
                });
            },
        }
    });
</script>
