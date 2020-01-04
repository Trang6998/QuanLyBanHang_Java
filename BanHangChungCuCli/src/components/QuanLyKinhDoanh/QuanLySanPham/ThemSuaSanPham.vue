<template>
    <v-dialog v-model="dialog" width="900" persistent scrollable>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>{{isUpdate ? 'Cập nhật sản phẩm' : 'Thêm mới sản phẩm'}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <v-form scope="frmAddEdit">
                        <v-layout row wrap>
                            <v-flex xs12 sm4>
                                <v-layout row wrap>
                                    <v-flex xs12>
                                        <v-tooltip bottom>
                                            <v-img v-if="sanPham.anhSanPham != null" slot="activator"
                                                   :src="sanPham.anhSanPham"
                                                   @click="$refs.inpFile.click()" style="max-width: 100%;" id="img"
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
                                                   @click="$refs.inpFile.click()" style="max-width: 100%;" id="img"
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
                                            <span>Nhấn để thay đổi ảnh đại diện</span>
                                        </v-tooltip>
                                        <input type="file" accept="image/*" style="display:none;" ref="inpFile" id="File" @change="changeImg()" capture="camera">

                                    </v-flex>
                                    <v-flex xs12>
                                        <v-btn small flat class="ma-0" aspect-ratio="1" @click="$refs.inpFile1.click()">
                                            <v-icon>add</v-icon>
                                            Ảnh mô tả sản phẩm
                                        </v-btn>
                                        <input type="file" accept="image/*" style="display:none;" ref="inpFile1" id="File1" @change="changeImgMedia()" capture="camera">
                                    </v-flex>
                                    <br />
                                    <v-flex xs12>
                                        <v-data-iterator :items="itemsAnh"
                                                         :pagination.sync="pagination"
                                                         content-tag="v-layout"
                                                         row
                                                         wrap
                                                         hide-actions>
                                            <template v-slot:item="props">
                                                <v-flex xs3>
                                                    <v-card style="position:relative" class="wrapper">
                                                        <v-img @click="ganAnhDaiDien(props.item.duongLink)" v-if="props.item.duongLink != null"
                                                               :src="props.item.duongLink"
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
                                                        <v-img @click="ganAnhDaiDien(props.item.duongLink)" v-else
                                                               src='/static/images/photo-3x4.jpg'
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

                                                        <v-btn @click="deleteMedia(props.item)" color="red" class="buttondelete white--text" small style="position: absolute; right:-14px; top: -14px; width: 18px; height: 18px" icon><v-icon style="font-size: 12px;" small>close</v-icon></v-btn>

                                                    </v-card>
                                                </v-flex>
                                            </template>
                                            <template slot="no-data">
                                                <p class="text-xs-center mb-0"></p>
                                            </template>
                                        </v-data-iterator>
                                        <div class="text-xs-center pt-2" v-if="itemsAnh.length !=0">
                                            <v-pagination v-model="pagination.page" :length="pages"></v-pagination>
                                        </div>
                                    </v-flex>
                                </v-layout>

                            </v-flex>
                            <v-flex xs12 sm8>
                                <v-layout row wrap>
                                    <v-flex xs12 sm6>
                                         <v-text-field v-model="sanPham.tenSanPham"
                                                      label="Tên sản phẩm"
                                                      type="text"
                                                      :error-messages="errors.collect('Tên sản phẩm', 'frmAddEdit')"
                                                      v-validate="'required'"
                                                      data-vv-scope="frmAddEdit"
                                                      data-vv-name="Tên sản phẩm"
                                                      clearable></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6>
                                        <v-autocomplete v-model="sanPham.loaiSanPhamID"
                                                        label="Danh mục"
                                                        :items="dsDanhMuc"
                                                        item-text="tenLoai"
                                                        item-value="loaiSanPhamID"
                                                        :error-messages="errors.collect('Danh mục', 'frmAddEdit')"
                                                        v-validate="'required'"
                                                        data-vv-scope="frmAddEdit"
                                                        data-vv-name="Danh mục"
                                                        clearable>
                                        </v-autocomplete>
                                    </v-flex>
                                    <v-flex xs12 sm6>
                                        <v-autocomplete v-model="sanPham.nhaCungCapID"
                                                        label="Nhà cung cấp"
                                                        :items="dsNhaCungCap"
                                                        item-text="tenNhaCungCap"
                                                        item-value="nhaCungCapID"
                                                        :error-messages="errors.collect('Nhà cung cấp', 'frmAddEdit')"
                                                        v-validate="'required'"
                                                        data-vv-scope="frmAddEdit"
                                                        data-vv-name="Nhà cung cấp"
                                                        clearable>
                                        </v-autocomplete>
                                    </v-flex>
                                    
                                    <v-flex xs12 sm4>
                                        <v-autocomplete v-model="sanPham.donViTinhID"
                                                        label="Đơn vị tính"
                                                        :items="dsDonVi"
                                                        item-text="tenDonVi"
                                                        item-value="donViTinhID"
                                                        :error-messages="errors.collect('Đơn vị tính', 'frmAddEdit')"
                                                        v-validate="'required'"
                                                        data-vv-scope="frmAddEdit"
                                                        data-vv-name="Đơn vị tính"
                                                        clearable>
                                        </v-autocomplete>
                                    </v-flex>
                                    
                                    <v-flex xs12 sm2>
                                        <v-text-field v-model="sanPham.thuTu"
                                                      label="Số thứ tự"
                                                      type="number"
                                                      :error-messages="errors.collect('Số thứ tự', 'frmAddEdit')"
                                                      v-validate="''"
                                                      data-vv-scope="frmAddEdit"
                                                      data-vv-name="Số thứ tự"></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm4>
                                        <v-text-field v-model="sanPham.kyHieuSanPham"
                                                      label="Ký hiệu sản phẩm"
                                                      type="text"
                                                      :error-messages="errors.collect('Ký hiệu sản phẩm', 'frmAddEdit')"
                                                      v-validate="''"
                                                      data-vv-scope="frmAddEdit"
                                                      data-vv-name="Ký hiệu sản phẩm"
                                                      clearable></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm4>
                                        <v-text-field v-model="sanPham.nhaSanXuat"
                                                      label="Nhà sản xuất"
                                                      type="text"
                                                      :error-messages="errors.collect('Nhà sản xuất', 'frmAddEdit')"
                                                      v-validate="''"
                                                      data-vv-scope="frmAddEdit"
                                                      data-vv-name="Nhà sản xuất"
                                                      clearable></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm4>
                                        <div class="v-input v-text-field theme--light ">
                                            <div class="v-input__control">
                                                <div class="v-input__slot">
                                                    <div class="v-text-field__slot">
                                                        <label aria-hidden="true" class="v-label theme--light" style="left: 0px; right: auto; position: absolute; margin-top: -20px!important ; font-size: 11px">Giá bán</label>
                                                        <money v-model="sanPham.giaBan"
                                                               v-bind="money"
                                                               :error-messages="errors.collect('Giá bán', 'frmAddEdit')"
                                                               v-validate="'required'"
                                                               data-vv-scope="frmAddEdit"
                                                               data-vv-name="Giá bán"></money>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </v-flex>
                                    <v-flex xs12>
                                        <v-textarea rows="3" v-model="sanPham.moTaNgan"
                                                    label="Mô tả ngắn"
                                                    :error-messages="errors.collect('Mô tả ngắn', 'frmAddEdit')"
                                                    v-validate="''"
                                                    data-vv-scope="frmAddEdit"
                                                    data-vv-name="Mô tả ngắn"
                                                    hide-details>
                                        </v-textarea>
                                    </v-flex>
                                    <v-flex xs12 sm4>
                                        <v-checkbox ma-0 hide-details label="Hiển thị nhà sản xuất" v-model="sanPham.anHienNhaSanXuat"></v-checkbox>

                                    </v-flex>
                                    <v-flex xs12 sm4>
                                        <v-checkbox ma-0 hide-details label="Hiển thị sản phẩm" v-model="sanPham.anHienSanPham"></v-checkbox>

                                    </v-flex>
                                    <v-flex xs12 sm4>
                                        <v-checkbox ma-0 hide-details label="Sản phẩm mới" v-model="sanPham.laSanPhamMoi"></v-checkbox>

                                    </v-flex>
                                    <v-flex xs12 v-if="sanPham.sanPhamID != null && sanPham.sanPhamID != 0 && sanPham.sanPhamID != undefined">
                                        <v-btn small flat color="primary" class="ma-0" @click="showModalThemThuocTinh()">
                                            <v-icon>add</v-icon>
                                            Thuộc tính sản phẩm ({{soThuocTinh}})
                                        </v-btn>
                                    </v-flex>
                                </v-layout>
                            </v-flex>
                            <v-flex xs12>
                                <h3>Nội dung mô tả sản phẩm:</h3>
                                <vue-ckeditor class="mt-1" v-model="sanPham.moTa"
                                              :config="config" />
                            </v-flex>
                        </v-layout>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn class="primary " :disabled="loading" :loading="loading" @click.native="save">{{isUpdate?'Cập nhật':'Thêm mới'}}</v-btn>
                </v-card-actions>
            </v-card>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">
                        Bạn có chắc chắn muốn xóa bản ghi này?
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteSanPham" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <them-sua-thuoc-tinh-san-pham ref="themSuaThuocTinhSanPham"></them-sua-thuoc-tinh-san-pham>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';
    import { SanPham } from '@/models/SanPham';
    import APIS from '@/apisServer';
    import { HTTP } from '@/http-servicesNew';
    import { Media } from '@/models/Media';
    import MediaApi from '@/apiResources/MediaApi';
    import ThemSuaThuocTinhSanPham from './ThemSuaThuocTinhSanPham.vue'
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { LoaiSanPham } from '@/models/LoaiSanPham';
    import { DonViTinh } from '@/models/DonViTinh';
    import DonViTinhApi, { DonViTinhApiSearchParams } from '@/apiResources/DonViTinhApi';
    import SanPhamNhaCungCapApi, { SanPhamNhaCungCapApiSearchParams } from '@/apiResources/SanPhamNhaCungCapApi';
    import { SanPhamNhaCungCap } from '@/models/SanPhamNhaCungCap';

    import VueCkeditor from '@/components/Commons/VueCkeditor.vue';
import NhaCungCapApi, { NhaCungCapApiSearchParams } from '@/apiResources/NhaCungCapApi';
import { NhaCungCap } from '@/models/NhaCungCap';
    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: { VueCkeditor, ThemSuaThuocTinhSanPham },
        data() {
            return {
                money: {
                    decimal: ",",
                    thousands: ".",
                    prefix: "",
                    suffix: " VNĐ",
                    precision: 0,
                    masked: false,
                    allowBlank: true,
                    min: 0
                },
                dialog: false,
                isUpdate: false,
                sanPham: {
                    anhSanPham: null as any,
                    media: [] as Media[],
                    sanPhamNhaCungCap: [] as SanPhamNhaCungCap[],
                    giaBan: 0
                } as SanPham,
                dsDanhMuc: [] as LoaiSanPham[],
                dsDonVi: [] as DonViTinh[],
                sanPhamID: 0,
                loading: false,
                searchParamsSanPham: {} as SanPhamApiSearchParams,
                APIS: APIS,
                soThuocTinh:0,
                config: {
                    toolbar: [
                        { name: 'document', items: ['Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates'] },
                        { name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo'] },
                        //{ name: 'editing', items: ['Find', 'Replace', '-', 'SelectAll', '-', 'Scayt'] },
                        //{ name: 'forms', items: ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'] },
                        { name: 'basicstyles', items: ['Bold', 'Italic', 'Underline', 'Strike', '-', 'CopyFormatting', 'RemoveFormat'] },
                        { name: 'colors', items: ['TextColor', 'BGColor'] },
                        { name: 'paragraph', items: ['NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
                        { name: 'links', items: ['Link', 'Unlink', 'Anchor'] },
                        { name: 'insert', items: ['Image', 'Table'] },
                        { name: 'styles', items: ['Styles', 'Format', 'Font', 'FontSize'] },
                        { name: 'tools', items: ['Maximize', 'ShowBlocks'] },
                        { name: 'about', items: ['About'] }
                    ],
                    height: 300
                },
                pagination: {
                    rowsPerPage: 4
                },
                itemsAnh: [] as any,
                searchParamsLoaiSanPham: { includeEntities: true, rowsPerPage: 0} as LoaiSanPhamApiSearchParams,
                dsSanPhamNhaCungCap: [] as SanPhamNhaCungCap[],
                dsSanPhamNhaCungCapLoading: false,
                searchSanPhamNhaCungCap: '',
                dsNhaCungCap: [] as NhaCungCap[],
                searchParamsNhaCungCap: { includeEntities: true, rowsPerPage: 0 } as NhaCungCapApiSearchParams,
                dialogConfirmDelete: false,
                dsToaNha: [] as any,
                daCoNCC: false
            }
        },
        watch: {
        },
        mounted() {
        },
        computed: {
            pages(): number {
                return Math.ceil(this.itemsAnh.length / 4);
            },
        },
        methods: {
            show(isUpdate: boolean, item: SanPham) {
                this.dialog = true;
                this.getDanhMuc();
                this.getDonVi();
                this.getNhaCungCap();
                this.$validator.errors.clear("frmAddEdit");
                this.isUpdate = isUpdate;
                if (this.isUpdate !== true) {
                    this.sanPham = {
                        anhSanPham: null as any,
                        anHienNhaSanXuat: true,
                        anHienSanPham: true,
                        sanPhamNhaCungCap: [] as SanPhamNhaCungCap[],
                        thuTu: 1,
                        media: [] as Media[]
                    } as SanPham;

                    this.itemsAnh = this.sanPham.media;
                }
                else {
                    this.sanPhamID = item.sanPhamID;
                    this.getDataFromApi(this.sanPhamID);
                }
            },
           
            getDataFromApi(id: number): void {
                SanPhamApi.detail(id).then(res => {
                    this.sanPham = res;
                    this.itemsAnh = res.media;
                    this.soThuocTinh = (res.thuocTinhSanPham as any).length;
                });
            },
            hide() {
                this.dialog = false;
            },
            ganAnhDaiDien(link: any) {
                this.sanPham.anhSanPham = link;
            },
            deleteMedia(media: Media) {
                var index = (this.sanPham.media as Media[]).indexOf(media);
                if (index > -1) {
                    (this.sanPham.media as Media[]).splice(index, 1);
                }
                if (media.sanPhamID === 0 || media.sanPhamID === null || media.sanPhamID === undefined) {

                }
                else {
                    MediaApi.delete(media.sanPhamID).then(res => { });
                }
            },
            showModalThemThuocTinh() {
                (this.$refs.themSuaThuocTinhSanPham as any).show(this.sanPhamID);
            },
            getDanhMuc() {
                this.searchParamsLoaiSanPham.laDanhMucCha = false;
                LoaiSanPhamApi.search(this.searchParamsLoaiSanPham).then(res => {
                    this.dsDanhMuc=res as any;
                });
            },
            getDonVi(){
                var DonViTinhApiSearchParams = {} as DonViTinhApiSearchParams;
                DonViTinhApi.search(DonViTinhApiSearchParams).then(res => {
                    this.dsDonVi=res as any;
                });
            },
            save(): void {
                (this.sanPham as any).tenLoai = undefined;
                this.sanPham.giaBan = parseInt(this.sanPham.giaBan.toString().replace(".", ""));
                this.$validator.validateAll('frmAddEdit').then((res) => {
                    if (res) {
                        this.sanPham.chiTietDonDatHang = undefined;
                        this.sanPham.loaiSanPham = undefined;
                        this.sanPham.thuocTinhSanPham = undefined;

                        if (this.isUpdate) {
                            this.sanPham.sanPhamNhaCungCap = undefined;
                            this.loading = true;
                            SanPhamApi.update(this.sanPham.sanPhamID, this.sanPham).then(res => {
                                this.dialog = false;
                                this.loading = false;
                                this.$emit("getData");
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            this.sanPham.ngayDang = new Date();
                            SanPhamApi.insert(this.sanPham).then(res => {
                                this.$emit("getData");
                                this.sanPhamID = res as any;
                                this.isUpdate = true;
                                this.loading = false;
                                this.$snotify.success('Thêm mới thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Thêm mới thất bại!');
                            });
                        }
                    }
                });
            },
            getNhaCungCap() {
                NhaCungCapApi.search(this.searchParamsNhaCungCap).then(res => {
                    this.dsNhaCungCap = res as any;
                });
            },
            changeImg() {
                var formData = new FormData()
                let files = (document as any).querySelector('#File').files[0]
                var re = /^\w+\.(?:jpg|png|gif|jpeg)$/g
                if (re.test(files.name)) {
                    formData.append('img', files)
                    HTTP.post('/api/img', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                        .then(res => {
                            this.sanPham.anhSanPham = res.data;
                        })
                } else {
                    this.$snotify.error('Tên file chỉ chứa ký tự chữ, số và dấu "_"');
                }
            },
            changeImgMedia() {
                var formData = new FormData()
                let files = (document as any).querySelector('#File1').files[0]
                var re = /^\w+\.(?:jpg|png|gif|jpeg)$/g
                if (re.test(files.name)) {
                    formData.append('img', files)
                    HTTP.post('/api/img', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                        .then(res => {
                            var media = {} as Media;
                            media.duongLink = res.data;
                            if (this.sanPhamID === 0 || this.sanPhamID === null || this.sanPhamID === undefined) {

                            }
                            else
                                media.sanPhamID = this.sanPhamID;
                            (this.sanPham.media as Media[]).push(media);

                        })
                } else {
                    this.$snotify.error('Tên file chỉ chứa ký tự chữ, số và dấu "_"');
                }
            }
        }
    });
</script>
<style>
    .ql-editing {
        left: 0px !important;
        top: -10px !important;
    }

    .theme--light.v-pagination .v-pagination__item {
        display: none !important;
        background: #fff;
        color: #000;
        width: auto;
        min-width: 34px;
        padding: 0 5px;
    }

    .v-responsive {
        cursor: pointer !important;
        position: relative;
        overflow: hidden;
        -webkit-box-flex: 1;
        -ms-flex: 1 0 auto;
        flex: 1 0 auto;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }

    .buttondelete {
        display: none;
    }

    .wrapper:hover img {
        /* Change the filter in here */
    }

    .wrapper:hover .buttondelete {
        display: inline-block;
    }
    .v-text-field__suffix {
        padding-left: 4px;
        white-space: nowrap;
        padding-right: 5px!important;
    }
</style>

