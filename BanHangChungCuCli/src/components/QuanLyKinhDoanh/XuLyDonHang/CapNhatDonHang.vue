<template>
    <v-dialog v-model="dialog" max-width="700" persistent>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>Cập nhật đơn {{donHang.soHieuDon}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text pa-0>
                    <v-layout row wrap>
                        <v-flex xs12>
                             <!-- :total-items="searchParamsChiTietDonDatHang.totalItems" -->
                            <v-data-table :headers="tableHeader"
                                          :items="dsChiTietDonDatHang"
                                          @update:pagination="getDataFromApi" :pagination.sync="searchParamsChiTietDonDatHang"
                                         
                                          :loading="loadingTable" hide-actions
                                          class="elevation-1" style="border-collapse: unset; background-color: unset!important">
                                <template slot="items" slot-scope="props">
                                    <td style="width: 40%; padding-left: 0px !important">
                                        <v-layout nowrap style="min-width: 200px">
                                            <v-flex xs4>
                                                <v-img v-if="props.item.sanPham && props.item.sanPham.anhSanPham != null" slot="activator"
                                                       :src="APIS.HOST + 'fileupload/download?key=' + props.item.anhSanPham"
                                                       style="max-width: 100%;" id="img"
                                                       aspect-ratio="1"
                                                       class="grey lighten-2">
                                                    <template v-slot:placeholder>
                                                        <v-layout fill-height
                                                                  align-center
                                                                  justify-center
                                                                  ma-0>
                                                            <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                                        </v-layout>
                                                    </template>
                                                </v-img>
                                                <v-img v-else src='/static/images/photo-3x4.jpg' slot="activator"
                                                       style="max-width: 100%;" id="img"
                                                       aspect-ratio="1"
                                                       class="grey lighten-2">
                                                    <template v-slot:placeholder>
                                                        <v-layout fill-height
                                                                  align-center
                                                                  justify-center
                                                                  ma-0>
                                                            <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                                        </v-layout>
                                                    </template>
                                                </v-img>
                                            </v-flex>
                                            <v-flex xs8>
                                                {{ props.item.sanPham ? props.item.sanPham.tenSanPham :'' }}
                                            </v-flex>
                                        </v-layout>
                                    </td>
                                    <td>{{ props.item.giaXuat | currency('', 0,{ thousandsSeparator: '.' })}}</td>
                                    <td>{{ props.item.soLuong }}</td>
                                    <td>{{ props.item.thanhTien | currency('', 0,{ thousandsSeparator: '.' }) }}</td>
                                    <td class="text-xs-center" style="width:80px;">
                                        <v-btn :disabled="donHang.tinhTrang == 0 || donHang.tinhTrang == 3 || donHang.tinhTrang == 4" flat color="red" small class="ma-0" @click="confirmDelete(props.item)">
                                            Xóa
                                        </v-btn>
                                    </td>
                                </template>
                                <template slot="no-data">
                                    <p class="text-xs-center mb-0">Không có dữ liệu</p>
                                </template>
                            </v-data-table>
                        </v-flex>
                        <v-flex xs12>
                            <v-layout nowrap style="font-size: 16px;">
                                <v-spacer></v-spacer>
                                <b>Tổng tiền: </b>&nbsp;<span style="color: chocolate">{{donHang.tongTien | currency('', 0,{ thousandsSeparator: '.' })}}</span>&nbsp;
                            </v-layout>
                        </v-flex>
                        <v-flex xs12>
                            <v-layout row wrap>
                                <v-flex xs12>
                                    Thời gian đặt: {{donHang.ngayDat |moment("hh:mm DD/MM/YYYY")}}
                                </v-flex>
                                <v-flex xs12>
                                    Thời gian hẹn:
                                    <span v-if="donHang.henLayTu != null && donHang.henLayDen != null">
                                        <span v-if="soSanhNgay(donHang.henLayTu, donHang.henLayDen)">
                                            {{ donHang.henLayTu === null ? "" : donHang.henLayTu|moment('HH:mm') }} -
                                            {{ donHang.henLayDen === null ? "" : donHang.henLayDen|moment('HH:mm') }}
                                            {{ donHang.henLayTu === null ? "" : donHang.henLayTu|moment('DD/MM/YY') }}
                                        </span>
                                        <span v-else>
                                            {{donHang.henLayTu |moment("hh:mm DD/MM/YYYY")}} -
                                            {{donHang.henLayDen |moment("hh:mm DD/MM/YYYY")}}
                                        </span>
                                       
                                    </span>
                                    <span v-else-if="donHang.henLayTu != null && donHang.henLayDen == null">
                                        Sau {{donHang.henLayTu |moment("hh:mm DD/MM/YYYY")}}
                                    </span>
                                    <span v-else-if="donHang.henLayTu == null && donHang.henLayDen != null">
                                        Trước {{donHang.henLayDen |moment("hh:mm DD/MM/YYYY")}}
                                    </span>
                                    <span v-else>
                                        Mọi thời gian
                                    </span>
                                </v-flex>

                            </v-layout>                        
                        </v-flex>
                        <v-flex xs12>
                            Ghi chú: {{donHang.ghiChu? donHang.ghiChu : "không có" }}
                        </v-flex>
                        <v-flex xs12>
                            <v-checkbox ma-0 v-model="donHang.laTre" label="Báo trễ đơn"></v-checkbox>
                        </v-flex>
                        <v-flex xs12 v-if="donHang.laTre">
                            <v-textarea rows="3" v-model="donHang.lyDoTraTre"
                                        label="Lý do trả trễ"
                                        :error-messages="errors.collect('Lý do trả trễ', 'frmAddEdit')"
                                        v-validate="''"
                                        data-vv-scope="frmAddEdit"
                                        data-vv-name="Lý do trả trễ"
                                        hide-details>
                            </v-textarea>
                        </v-flex>
                        <v-flex xs12 v-if="donHang.laTre">
                            <v-layout row wrap>
                                <v-flex xs12>
                                    Thời gian trả dự kiến:
                                </v-flex>
                                <v-flex xs12>
                                    <v-layout nowrap>
                                        <v-flex xs6>
                                            <v-datetimepicker label="Từ ngày" hide-details v-model="donHang.henTraTu"></v-datetimepicker>
                                        </v-flex>
                                        <v-flex xs6>
                                            <v-datetimepicker label="Đến ngày" hide-details v-model="donHang.henTraDen"></v-datetimepicker>
                                        </v-flex>
                                    </v-layout>
                                </v-flex>

                            </v-layout>

                        </v-flex>
                    </v-layout>
                    <v-layout row wrap v-if="daNhanDon">
                        <v-flex xs12>
                            <v-layout nowrap>
                                <v-radio-group hide-details v-model="donHang.tinhTrang" row>
                                    <v-radio label="Đã chuyển hàng-chưa thanh toán" :value="3"></v-radio>
                                    <v-radio label="Hoàn thành" :value="4"></v-radio>
                                </v-radio-group>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                    <v-layout row wrap v-else>
                        <v-flex xs12>
                            <v-layout nowrap>
                                <v-spacer></v-spacer>
                                <v-btn flat small color="primary" :disabled="loading" :loading="loading" @click="nhanDon">Nhận đơn</v-btn>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn class="primary" @click="capNhatTrangThai(0)" small :disabled="loading" :loading="loading">Cập nhật</v-btn>
                </v-card-actions>
            </v-card>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteChiTietDonDatHang" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import { DonDatHang } from '@/models/DonDatHang';
    import DonDatHangApi from '@/apiResources/DonDatHangApi';
    import APIS from '@/apisServer';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                loading: false,
                dialog: false,
                donDatHang: {} as DonDatHang,
                dsChiTietDonDatHang: [] as ChiTietDonDatHang[],
                tableHeader: [
                    { text: 'Sản phẩm', value: '#', align: 'left', sortable: false },
                    { text: 'Đơn giá', value: 'donGia', align: 'left', sortable: false },
                    { text: 'Số lượng', value: 'soLuong', align: 'left', sortable: false },
                    { text: 'Số tiền', value: 'soTien', align: 'left', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'left', sortable: false },
                ],
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 0 } as ChiTietDonDatHangApiSearchParams,
                loadingTable: false,
                selectedChiTietDonDatHang: {} as ChiTietDonDatHang,
                dialogConfirmDelete: false,
                APIS: APIS,
                daNhanDon: false,
                donHang: {} as DonDatHang,
                laTre: false,
                lyDo: '',
                trangThaiDon: -1
            }
        },
        watch: {
        },
        mounted() {
        },
        methods: {
            show(id: number) {
                this.dialog = true;
                this.searchParamsChiTietDonDatHang.donDatHangID = id;
                this.getDataFromApi(this.searchParamsChiTietDonDatHang);
                DonDatHangApi.detail(id).then(res => {
                    this.donHang = res;
                    this.trangThaiDon = res.tinhTrang;
                    if (res.tinhTrang === 1 || res.tinhTrang === 0)
                        this.daNhanDon = false;
                    else
                        this.daNhanDon = true;
                })
            },
            hide() {
                this.dialog = false;
            },
            nhanDon() {
                this.donHang.tinhTrang = 2 // đã nhận đơn - đang xử lý
                DonDatHangApi.update(this.donHang.donDatHangID, this.donHang).then(res => {
                    this.$emit("getData");
                    this.$eventBus.$emit('UpdateSoDonChuaNhan', 0);
                    this.daNhanDon = true;
                    this.$snotify.success('Nhận đơn số ' + this.donHang.soHieuDon + ' thành công!');
                }).catch(res => {
                    this.$snotify.error('Nhận đơn thất bại!');
                });
            },
            showModalBaoTreDonHang() {

            },
            capNhatTrangThai() {
                if (this.donHang.tinhTrang == 4 || this.donHang.tinhTrang == 3) {
                    this.donHang.ngayGioTraThucTe = this.$moment();
                }
                DonDatHangApi.update(this.donHang.donDatHangID, this.donHang).then(res => {
                    this.dialog = false;
                    this.$emit("getData");
                    this.$snotify.success('Cập nhật trạng thái đơn thành công!');
                }).catch(res => {
                    this.$snotify.error('Cập nhật trạng thái đơn thất bại!');
                });
            },
            getDataFromApi(searchParamsChiTietDonDatHang: ChiTietDonDatHangApiSearchParams): void {
                this.loadingTable = true;
                ChiTietDonDatHangApi.search(searchParamsChiTietDonDatHang).then(res => {
                    this.dsChiTietDonDatHang = res as any;
                    //this.searchParamsChiTietDonDatHang.totalItems = (res as any).length;
                    this.loadingTable = false;
                });
            },
            confirmDelete(chiTietDonDatHang: ChiTietDonDatHang): void {
                this.selectedChiTietDonDatHang = chiTietDonDatHang;
                this.dialogConfirmDelete = true;
            },
            deleteChiTietDonDatHang(): void {
                ChiTietDonDatHangApi.delete(this.selectedChiTietDonDatHang.chiTietDonDatHangID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsChiTietDonDatHang);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
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
</style>

