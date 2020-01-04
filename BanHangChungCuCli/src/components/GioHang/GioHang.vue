<template>
  <v-flex xs12>
    <v-breadcrumbs divider="/" class="pa-0">
      <v-toolbar-title>Giỏ hàng</v-toolbar-title>
    </v-breadcrumbs>
    <v-layout row wrap>
        <v-flex xs12 sm9>
            <v-layout v-if="lstGioHang.length == 0">
                <v-flex xs12>
                    <v-card>
                        <p style="font-size: 18px; text-align: center">Giỏ hàng trống</p>
                    </v-card>
                </v-flex>
            </v-layout>
            <v-layout row wrap>
                <template v-for="item in lstGioHang">
                    <v-flex xs12>
                        <v-card style="padding-bottom: 1px">
                            <v-layout nowrap>
                                <v-flex xs3 sm2>
                                    <v-img v-if="item.sanPham && item.sanPham.anhSanPham != null" slot="activator"
                                           :src="item.sanPham.anhSanPham"
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
                                <v-flex xs9 sm10>
                                    <v-card-title primary-title>
                                        <v-layout nowrap>
                                            <v-flex xs9>
                                                <div style="font-size: 18px">{{item.sanPham ? item.sanPham.tenSanPham : ''}}</div>
                                            </v-flex>
                                            <v-flex xs3>
                                                <v-layout nowrap>
                                                    <v-spacer></v-spacer>
                                                    <v-btn flat small @click="confirmDelete(item)" style="margin: 0px; padding: 0px" color="red">
                                                        Xóa
                                                    </v-btn>
                                                </v-layout>
                                            </v-flex>
                                        </v-layout>
                                    </v-card-title>
                                    <v-card-title primary-title>
                                        <div style="font-size: 14px; color: dimgrey; padding-left:0px">{{item.giaXuat | currency('₫', 0, { thousandsSeparator: '.' })}} &nbsp;x&nbsp;</div>
                                        <div class="shopee-input-quantity">
                                            <!--<div style="font-size: 14px; color: dimgrey; padding-left:0px">Số lượng:&nbsp;&nbsp;</div>-->
                                            
                                            <button class="shopee-button-outline" style="font-size: 14px" @click="donHang.tongTien = ((item.soLuong>1)? donHang.tongTien - item.giaXuat : donHang.tongTien); item.soLuong = (item.soLuong > 1? item.soLuong - 1 : 1); save(item)"><v-icon>remove</v-icon></button>
                                            <input class="shopee-button-outline shopee-button-outline-mid" style="font-size: 14px; color: dimgrey" readonly type="number" min="1" v-model.number="item.soLuong" />
                                            <button class="shopee-button-outline" style="font-size: 14px" @click="item.soLuong = item.soLuong + 1 ; donHang.tongTien = donHang.tongTien + item.giaXuat; save(item)"><v-icon>add</v-icon></button>
                                        </div>

                                    </v-card-title>
                                </v-flex>
                            </v-layout>
                        </v-card>
                    </v-flex>
                </template>
            </v-layout>
        </v-flex>
        <v-flex xs12 sm3>
            <v-layout nowrap>
                <v-flex xs12>
                    <v-card>
                        <v-layout row wrap>
                            <v-flex xs8 sm12>
                                <v-layout nowrap style="padding: 15px">
                                    <b class="headline">Tổng tiền:&nbsp;</b>
                                    <div style="color: red; font-size: 18px" v-if="donHang.tongTien != null"> {{ donHang.tongTien | currency('', 0,{ thousandsSeparator: '.' })}}</div>
                                    <div style="color: red; font-size: 18px" v-if="donHang.tongTien == null"> {{ 0 | currency('', 0,{ thousandsSeparator: '.' })}}</div>
                                </v-layout>
                            </v-flex>
                            <v-flex xs4 sm12>
                                <v-layout nowrap style="padding-top: 15px">
                                    <v-spacer></v-spacer>
                                    <v-btn small v-if="donHang.donDatHangID != 0 && donHang.donDatHangID != null && donHang.donDatHangID != undefined" style="font-size:18px!important" color="primary" :disabled="loading" :loading="loading" @click="showModalMuaHang">Mua hàng</v-btn>
                                </v-layout>
                            </v-flex>
                        </v-layout>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-flex>
        <dat-hang-thanh-cong ref="datHangThanhCong"></dat-hang-thanh-cong>
        <v-dialog v-model="dialogConfirmDelete" max-width="290">
            <v-card>
                <v-card-title class="headline">Xác nhận xóa</v-card-title>
                <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                    <v-btn color="red darken-1" @click="deleteChiTietDonDatHang" flat>Xác Nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
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
                                <v-datetimepicker v-model="donHang.henLayTu" label="Từ ngày" :min="new Date()"
                                                  clearable></v-datetimepicker>
                            </v-flex>
                            <v-flex xs12 md6 v-if="thoigian == 'theokhunggio'">
                                <v-datetimepicker v-model="donHang.henLayDen" label="Đến ngày" :min="thoiGianHenToiThieu"
                                                  clearable></v-datetimepicker>
                            </v-flex>
                            <v-flex xs12>
                                <v-textarea rows="3" v-model="donHang.ghiChu"
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
    </v-layout>
  </v-flex>
</template>
<style>
 v-card-actions {
     padding: 0px;
 }
</style>
<script lang="ts">
    import APIS from '@/apisServer';
    import { Vue } from 'vue-property-decorator';
    import DonDatHangApi, { DonDatHangApiSearchParams, GioHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { DonDatHang } from "@/models/DonDatHang";
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
    import DatHangThanhCong from '../GioHang/DatHangThanhCong.vue'
    export default Vue.extend ({
        components: { DatHangThanhCong},
        data() {
            return {
                thoigian: 'moithoigian',
                searchParamsGioHang: { includeEntities: true, rowsPerPage: 0, taiKhoanKhachHangID: this.$store.state.user.User.userId } as GioHangApiSearchParams,
                date: new Date().toISOString().substr(0, 10),
                menu: false,
                modal: false,
                menu2: false,
                tableHeader: [
                { text: 'Sản phẩm', value: 'calories' },
                { text: 'Đơn giá', value: 'fat' },
                { text: 'Số lượng', value: 'carbs' },
                { text: 'Thành tiền', value: 'protein' },
                { text: 'Thao tác', value: 'iron' }
                ],
                dialogConfirmDelete: false,
                dialogConfirm: false,
                isUpdate: false,
                gioHang: {} as any,
                donHang: {} as DonDatHang,
                lstGioHang: [] as any,
                APIS: APIS,
                loading: false,
                selectedChiTietDonDatHang: {} as ChiTietDonDatHang,
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 10 } as ChiTietDonDatHangApiSearchParams,
                thoiGianHenToiThieu: null as any
            }
        },
        created() {
            this.getDataFromApi();
        },
        watch: {
            'donHang.henLayTu': function (val) {
                this.thoiGianHenToiThieu = this.$moment(val).add(15, 'm');
            },
        },
        methods: {
            getDataFromApi(): void {
                DonDatHangApi.getgiohang(this.searchParamsGioHang).then(res => {
                    this.lstGioHang = res as any;
                    if (this.lstGioHang.length >0 && this.lstGioHang[0].donDatHangID != 0 && this.lstGioHang[0].donDatHangID != null && this.lstGioHang[0].donDatHangID != undefined)
                        this.getDonHang(this.lstGioHang[0].donDatHangID);
                    else {
                        this.donHang = {} as DonDatHang;
                        (this.donHang as any).tongTien = 0
                    }
                    //this.searchParamsGioHang.totalItems = (res as any).chiTietGioHang.pagination.totalItems;
                });
            },
            getDonHang(id: number) {
                DonDatHangApi.detail(id).then(res => {
                    this.donHang = res;
                    this.searchParamsChiTietDonDatHang.donDatHangID = res.donDatHangID;
                });
            },
            muaNgay() {
                this.donHang.tinhTrang = 1 // mua hàng
                this.donHang.ngayDat = this.$moment();
                (this.donHang as any).tongTien = undefined;
                (this.donHang as any).chiTietDonDatHangs = undefined;
                (this.donHang as any).lstChiTietDonDatHang = this.lstGioHang;
                //this.donHang.chiTietDonDatHang = this.lstGioHang;
                DonDatHangApi.update(this.donHang.donDatHangID, this.donHang).then(res => {
                    this.getDataFromApi();
                    this.dialogConfirm = false;
                    this.$snotify.success('Mua hàng thành công');
                    this.$eventBus.$emit('UpdateGioHang', 0);
                    this.$eventBus.$emit('UpdateSoDonChuaNhan', 0);
                    (this.$refs.datHangThanhCong as any).show(res as any);
                }).catch(res => {
                    this.$snotify.error('Có lỗi xảy ra, vui lòng thử lại!');
                });
            },
            confirmDelete(chiTietDonDatHang: ChiTietDonDatHang): void {
                this.selectedChiTietDonDatHang = chiTietDonDatHang;
                this.dialogConfirmDelete = true;
            },
            deleteChiTietDonDatHang(): void {
                ChiTietDonDatHangApi.delete(this.selectedChiTietDonDatHang.chiTietDonDatHangID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi();
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
            save(item : ChiTietDonDatHang): void {
                ChiTietDonDatHangApi.update(item.chiTietDonDatHangID, item).then(res => {
                    console.log("ok");
                }).catch(res => {
                    this.$snotify.error('Cập nhật số lượng thất bại!');
                });
            },
            confirm() {
                this.dialogConfirm = true;
            },
            showModalMuaHang() {
                this.dialogConfirm = true;
            }
        }
    })
</script>
