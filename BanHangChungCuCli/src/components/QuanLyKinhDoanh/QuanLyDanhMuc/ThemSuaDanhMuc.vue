<template>
    <v-dialog v-model="dialog" width="600" persistent scrollable>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>{{isUpdate ? 'Cập nhật danh mục' : 'Thêm mới danh mục'}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <v-form scope="frmAddEdit">
                        <v-layout row wrap>
                            <v-flex xs4>
                                <v-tooltip bottom>
                                    <v-img v-if="danhMuc.anhDaiDien != null" slot="activator"
                                           :src="APIS.HOST + 'fileupload/download?key=' + danhMuc.anhDaiDien"
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
                            <v-flex xs8>
                                <v-layout row wrap>

                                    <v-flex xs12 sm6>
                                        <v-text-field v-model="danhMuc.tenLoai"
                                                      label="Tên danh mục"
                                                      type="text"
                                                      :error-messages="errors.collect('Tên danh mục', 'frmAddEdit')"
                                                      v-validate="'required'"
                                                      data-vv-scope="frmAddEdit"
                                                      data-vv-name="Tên danh mục"
                                                      clearable></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6>
                                        <v-autocomplete v-model="danhMuc.loaiSanPhamPID"
                                                        v-show="showDanhMucCha"
                                                        :items="dsDanhMucCha"
                                                        label="Danh mục cha"
                                                        item-text="tenLoai"
                                                        item-value="loaiSanPhamID">
                                        </v-autocomplete>
                                    </v-flex>

                                    <v-flex xs12>
                                        <v-textarea rows="3" v-model="danhMuc.moTa"
                                                    label="Mô tả"
                                                    :error-messages="errors.collect('Mô tả', 'frmAddEdit')"
                                                    v-validate="''"
                                                    data-vv-scope="frmAddEdit"
                                                    data-vv-name="Mô tả"
                                                    hide-details>
                                        </v-textarea>
                                    </v-flex>
                                </v-layout>
                            </v-flex>

                        </v-layout>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn class="primary " :disabled="loading" :loading="loading" @click.native="save">{{isUpdate?'Cập nhật':'Thêm mới'}}</v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { LoaiSanPham } from '@/models/LoaiSanPham';
    import APIS from '@/apisServer';
    import { HTTP } from '@/HTTPServices';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {  },
        data() {
            return {
                dialog: false,
                isUpdate: false,
                danhMuc: { anhDaiDien: null as any } as LoaiSanPham,
                loaiSanPhamid: 0,
                loading: false,
                searchParamsLoaiSanPham: {} as LoaiSanPhamApiSearchParams,
                APIS: APIS,
                dsDanhMucCha: [] as LoaiSanPham[],
                showDanhMucCha: true
            }
        },
        watch: {
        },
        mounted() {
        },
        computed: {
        },
        methods: {
            show(isUpdate: boolean, item: LoaiSanPham, laCha: boolean) {
                this.dialog = true;
                this.$validator.errors.clear("frmAddEdit");
                this.getDanhMucCha();
                this.isUpdate = isUpdate;
                
                if (this.isUpdate !== true) {
                    this.danhMuc = {
                        anhDaiDien: null as any,
                    } as LoaiSanPham;
                    this.showDanhMucCha = true
                }
                else {
                    if (laCha === "true" as any) this.showDanhMucCha = false;
                    else this.showDanhMucCha = true;
                    this.loaiSanPhamid = item.loaiSanPhamID;
                    this.getDataFromApi(this.loaiSanPhamid);
                }
            },
            getDataFromApi(id: number): void {
                LoaiSanPhamApi.detail(id).then(res => {
                    this.danhMuc = res;
                });
            },
            hide() {
                this.dialog = false;
            },
            getDanhMucCha() {
                this.searchParamsLoaiSanPham.laDanhMucCha = true;
                LoaiSanPhamApi.search(this.searchParamsLoaiSanPham).then(res => {
                    this.dsDanhMucCha = res as any;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                    if (res) {
                        //this.danhMuc.loaiSanPham1 = undefined;
                        if (this.isUpdate) {
                            this.loading = true;
                            LoaiSanPhamApi.update(this.danhMuc.loaiSanPhamID, this.danhMuc).then(res => {
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
                            LoaiSanPhamApi.insert(this.danhMuc).then(res => {
                                this.dialog = false;
                                this.$emit("getData");
                                this.danhMuc = res;
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
            changeImg() {
                var formData = new FormData()
                let files = (document as any).querySelector('#File').files[0]
                var re = /^\w+\.(?:jpg|png|gif|jpeg)$/g
                if (re.test(files.name)) {
                    formData.append('img', files)
                    HTTP.post('/img', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                        .then(res => {
                            this.danhMuc.anhDaiDien = res.data;
                        })
                } else {
                    this.$snotify.error('Tên file chỉ chứa ký tự chữ, số và dấu "_"');
                }
            },
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
</style>

