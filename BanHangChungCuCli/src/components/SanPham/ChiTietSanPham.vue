<template>
  <v-flex xs12>
      <v-breadcrumbs divider="/" class="pa-0">
          <v-icon slot="divider">chevron_right</v-icon>
          <!--<v-breadcrumbs-item>
              <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
          </v-breadcrumbs-item>-->
          <v-breadcrumbs-item to="/danh-muc-san-pham" exact>Danh mục</v-breadcrumbs-item>
          <v-breadcrumbs-item v-if="tenLoaiCha!=null" :to="'/danh-muc-san-pham/' + danhMucChaID" exact>{{tenLoaiCha}}</v-breadcrumbs-item>
          <v-breadcrumbs-item :to="'/danh-muc-san-pham/' + sanPham.loaiSanPhamID" exact>{{sanPham.tenLoai}}</v-breadcrumbs-item>
          <v-breadcrumbs-item>{{sanPham.tenSanPham}}</v-breadcrumbs-item>
      </v-breadcrumbs>
    <hr class="mt-3 mb-3">
    <v-layout row wrap>
        <v-card style="width: 100%; margin-bottom: 20px">
            <v-layout row wrap style="padding: 15px">
                <v-flex xs12 sm5>
                    <template>
                        <v-carousel style="max-height: 400px">
                            <v-carousel-item v-for="(item,i) in sanPham.media"
                                             :key="i" style="max-height: 400px"
                                             :src="APIS.HOST + 'fileupload/download?key=' + item.duongLink"
                                             aspect-ratio="1" class="grey lighten-2">
                            </v-carousel-item>
                        </v-carousel>
                    </template>
                </v-flex>
                <v-flex xs12 sm7>
                    <v-card-text style="padding: 0px">
                        <v-card-title primary-title>
                            <h3 class="headline mb-0" style="font-size:30px !important">{{ sanPham.tenSanPham}}</h3>
                        </v-card-title>
                        <v-card-title>
                            <span style="color:red; font-size:28px; font-weight:500">{{sanPham.giaBan |currency('', 0)}} <u> đ</u></span>
                        </v-card-title>
                        <v-card-actions>
                            <v-layout row wrap>
                                <v-flex xs12 v-if="sanPham.nhaSanXuat">
                                    <div style="font-size: 20px;color: dimgrey;padding-left: 4px">Nhà sản xuất: {{sanPham.nhaSanXuat}}</div>
                                </v-flex>
                                <v-flex xs12 v-if="sanPham.moTaNgan != null">
                                    <div style="font-size: 20px;color: dimgrey; padding-left: 4px">
                                        Mô tả ngắn:
                                        <p>{{sanPham.moTaNgan}}</p>
                                    </div>
                                </v-flex>
                                <v-flex xs12>
                                    <div class="shopee-input-quantity">
                                        <div style="font-size: 18px; color: dimgrey; padding-left: 4px">Số lượng:&nbsp;&nbsp;</div>
                                        <button class="shopee-button-outline" style="font-size: 18px" @click="thayDoiSoLuong(false)"><v-icon>remove</v-icon></button>
                                        <input class="shopee-button-outline shopee-button-outline-mid" style="font-size: 18px; color: dimgrey" type="number" min="1" v-model="soLuong" />
                                        <button class="shopee-button-outline" style="font-size: 18px" @click="thayDoiSoLuong(true)"><v-icon>add</v-icon></button>
                                        <div style="font-size: 18px; color: dimgrey">&nbsp;&nbsp;{{sanPham.donViTinh ? sanPham.donViTinh.tenDonVi : ''}}</div>
                                    </div>
                                </v-flex>
                                <v-flex xs12>
                                    <v-btn flat color="primary" style="color:#fff; font-size:18px; border-color: red" :loading="loading" :disabled="loading" @click="themVaoGioHang()">
                                        <v-icon>add_shopping_cart</v-icon>Thêm Vào Giỏ
                                    </v-btn>
                                    <v-btn color="primary" style="background-color:#f6821f; font-size:18px" class="ml-2" @click="showModalMuaNgay">
                                        Mua Ngay
                                    </v-btn>
                                </v-flex>
                            </v-layout>
                        </v-card-actions>
                    </v-card-text>
                </v-flex>
            </v-layout>
        </v-card>
        <v-card style="width: 100%">
            <v-layout row wrap style="padding: 15px">
                <v-flex xs12 v-if="sanPham.thuocTinhSanPham.length != 0">
                    <div class="headerCT">CHI TIẾT SẢN PHẨM</div>
                </v-flex>
                <v-flex xs12 v-if="sanPham.thuocTinhSanPham.length != 0">
                    <div class="_2aZyWI">
                        <div class="kIo6pj" v-for="(item,i) in sanPham.thuocTinhSanPham" :key="i">
                            <label class="_1ymsZN">
                                {{item.tenThuocTinh}}
                            </label>
                            <div>{{item.noiDungMoTa}}</div>
                        </div>
                    </div>
                </v-flex>
                <v-flex xs12>
                    <div class="headerCT">MÔ TẢ SẢN PHẨM</div>
                </v-flex>
                <v-flex xs12>
                    <div class="_2aZyWI">
                        <div>
                            <span v-html="sanPham.moTa"></span>
                        </div>
                    </div>

                </v-flex>
            </v-layout>
        </v-card>
        <dat-hang-thanh-cong ref="datHangThanhCong"></dat-hang-thanh-cong>

        <v-dialog v-model="dialogMuaNgay" max-width="600">
            <v-container pa-0 grid-list-md>
                <v-card>
                    <v-card-title class="primary white--text" style="height: 40px">
                        <h3>Mua hàng</h3>
                        <v-spacer></v-spacer>
                        <v-btn class="white--text ma-0" small @click="hideDonMuaNgay" icon dark><v-icon>close</v-icon></v-btn>
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
                            <v-btn class="primary" flat small @click="muaNgay">Mua ngay</v-btn>
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
    import { Vue } from 'vue-property-decorator';
    import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';
    import SanPhamApi1, { SanPhamThemVaoGioApiParams } from '@/apiResources/SanPhamApi';
    import { SanPham } from '@/models/SanPham';
    import APIS from '@/apisServer';
    import { DonDatHang } from '@/models/DonDatHang';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import LoaiSanPhamApi from '@/apiResources/LoaiSanPhamApi';
    import DatHangThanhCong from '../GioHang/DatHangThanhCong.vue'

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: { DatHangThanhCong},
        data() {
            return {
                thoigian: 'moithoigian',
                loading: false,
                isUpdate: false,
                sanPham: {} as SanPham,
                chiTietDonHang: {} as ChiTietDonDatHang,
                donDatHang: {} as DonDatHang,
                searchParamsSanPham: { includeEntities: true, rowsPerPage: 10 } as SanPhamApiSearchParams,
                paramsThemVaoGio: {} as SanPhamThemVaoGioApiParams,
                selectedSanPham: {} as SanPham,
                APIS: APIS,
                tenLoaiCha: null as any,
                danhMucChaID: 0,
                soLuong: 1,
                dialogMuaNgay: false,
                donMuaNgay: {
                    lstChiTietDonDatHang: [] as ChiTietDonDatHang[]
                } as DonDatHang,
                chiTietDonMuaNgay: {} as ChiTietDonDatHang,
                thoiGianHenToiThieu: null as any,
            }
        },
        mounted() {
            if (this.$route.name === 'ChiTietSanPham') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        watch: {
            'donMuaNgay.henLayTu': function (val) {
                this.thoiGianHenToiThieu = this.$moment(val).add(15, 'm');
            },
        },
        created() {
        },
        methods: {
            thayDoiSoLuong(tang: boolean) {
                if (tang === true) this.soLuong = this.soLuong + 1;
                else {
                    if (this.soLuong > 1)
                        this.soLuong = this.soLuong - 1;
                }
            },
            showModalMuaNgay() {
                this.dialogMuaNgay = true;
                this.chiTietDonMuaNgay.soLuong = this.soLuong;
                this.chiTietDonMuaNgay.sanPhamID = this.sanPham.sanPhamID;
                this.chiTietDonMuaNgay.giaXuat = this.sanPham.giaBan;
                this.donMuaNgay.taiKhoanDatHangID = this.$store.state.user.User.UserId;
                this.donMuaNgay.ngayDat = this.$moment();
                this.donMuaNgay.tinhTrang = 1;
            },
            hideDonMuaNgay() {
                this.dialogMuaNgay = false;
            },
            muaNgay() {
                (this.donMuaNgay.lstChiTietDonDatHang as ChiTietDonDatHang[]).push(this.chiTietDonMuaNgay);
                DonDatHangApi.insert(this.sanPham.sanPhamID, this.soLuong, this.sanPham.giaBan,this.donMuaNgay).then(res => {
                    this.$snotify.success('Đặt mua thành công!');
                    this.dialogMuaNgay = false;
                    this.$eventBus.$emit('UpdateSoDonChuaNhan', 0);
                    (this.$refs.datHangThanhCong as any).show(res as any);
                    this.donMuaNgay = {
                        lstChiTietDonDatHang: [] as ChiTietDonDatHang[]
                    } as DonDatHang;
                    this.chiTietDonMuaNgay = {} as ChiTietDonDatHang;
                });
            },
            themVaoGioHang() {
                this.loading = true;
                this.paramsThemVaoGio.sanPhamID = this.sanPham.sanPhamID;
                this.paramsThemVaoGio.soLuong = this.soLuong;
                this.paramsThemVaoGio.giaXuat = this.sanPham.giaBan;
                this.paramsThemVaoGio.taiKhoanKhachHangID = this.$store.state.user.User.UserId;
                SanPhamApi.insertCart(this.paramsThemVaoGio).then(res => {
                    this.loading = false;
                    this.$eventBus.$emit('UpdateGioHang', 0);
                    this.$snotify.success('Thêm vào giỏ hàng thành công!');
                }).catch(res => {
                    this.loading = false;
                    this.$snotify.error('Có lỗi xảy ra, vui lòng thử lại!');
                });
            },
            getDataFromApi(id: number): void {
                SanPhamApi.detail(id).then(res => {
                    this.sanPham = res;
                    this.getTenDanhMucCha(res.loaiSanPhamID);
                });
            },
            getTenDanhMucCha(id: number) {
                LoaiSanPhamApi.detail(id).then(res => {
                    if ((res as any).danhMucCha !== null) {
                        this.tenLoaiCha = (res as any).tenLoaiCha;
                        this.danhMucChaID = res.loaiSanPhamPID; 
                    }
                    else
                        this.tenLoaiCha = null;
                });
            },
           
        }
    });
</script>
<style>
    ._2aZyWI {
        margin: 1.875rem .9375rem .9375rem;
    }
    .kIo6pj {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        margin-bottom: 1.125rem;
    }
    ._2u0jt9 {
        white-space: pre-wrap;
        color: rgba(0,0,0,.8);
        font-size: 16px;
        overflow: hidden;
        text-overflow: ellipsis;
        line-height: 1.875rem;
    }
    ._1ymsZN {
        color: rgba(0,0,0,.4);
        font-size: 16px;
        width: 6.25rem;
        margin-right: 2.5rem;
    }
    ._1z1CEl {
        height: .9375rem;
    }

    .items-center {
        -webkit-box-align: center;
        -webkit-align-items: center;
        -moz-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }
    ._1FzU2Y .shopee-input-quantity {
        background: #fff;
    }
    .headerCT {
        background: rgba(0,0,0,.02);
        color: rgba(0,0,0,.87);
        font-size: 20px;
        padding: .875rem;
        text-transform: capitalize;
    }
    .shopee-input-quantity {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -moz-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }
    .shopee-input-quantity > .shopee-button-outline:first-child {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
    }

    .shopee-input-quantity .shopee-button-outline {
        width: 2rem;
        height: 2rem;
    }

    .shopee-button-outline {
        outline: none;
        cursor: pointer;
        border: none;
        font-size: .875rem;
        font-weight: 300;
        line-height: 1;
        letter-spacing: 0;
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -moz-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        -webkit-box-pack: center;
        -webkit-justify-content: center;
        -moz-box-pack: center;
        -ms-flex-pack: center;
        justify-content: center;
        -webkit-transition: background-color .1s cubic-bezier(.4,0,.6,1);
        transition: background-color .1s cubic-bezier(.4,0,.6,1);
        border: 1px solid rgba(0,0,0,.09);
        border-radius: 2px;
        background: transparent;
        color: rgba(0,0,0,.8);
    }
    .shopee-input-quantity .shopee-button-outline-mid {
        width: 3.125rem;
        height: 2rem;
        border-left: none;
        border-right: none;
        font-size: 1rem;
        font-weight: 400;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        text-align: center;
        cursor: text;
        border-radius: 0;
    }
    button, html input[type=button], input[type=reset], input[type=submit] {
        -webkit-appearance: button;
        cursor: pointer;
    }

    button, select {
        text-transform: none;
    }

    button {
        overflow: visible;
    }

    button, input, optgroup, select, textarea {
        color: inherit;
        font: inherit;
        margin: 0;
    }
    button {
        cursor: pointer;
    }
    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

</style>