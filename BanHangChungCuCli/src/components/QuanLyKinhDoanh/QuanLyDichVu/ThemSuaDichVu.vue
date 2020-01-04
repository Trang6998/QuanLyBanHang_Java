<template>
    <v-dialog v-model="dialog" width="800" persistent scrollable>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>{{isUpdate ? 'Cập nhật dịch vụ' : 'Thêm mới dịch vụ'}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <v-form scope="frmAddEdit">
                        <v-layout row wrap>
                            <v-flex xs4>
                                <v-layout row wrap>
                                    <v-flex xs12>
                                        <v-tooltip bottom>
                                          
                                            <v-img v-if="dichVu.anhDaiDien != null" slot="activator"
                                                   :src="dichVu.anhDaiDien"
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
                                            Ảnh mô tả dịch vụ
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
                            <v-flex xs8>
                                <v-layout row wrap>
                                    <v-flex xs12>
                                        <v-text-field v-model="dichVu.tenDichVu"
                                                      label="Tên dịch vụ"
                                                      type="text"
                                                      :error-messages="errors.collect('Tên dịch vụ', 'frmAddEdit')"
                                                      v-validate="'required'"
                                                      data-vv-scope="frmAddEdit"
                                                      data-vv-name="Tên dịch vụ"
                                                      clearable></v-text-field>
                                    </v-flex>
                                   
                                    <v-flex xs12>
                                        <v-textarea rows="3" v-model="dichVu.moTaNgan"
                                                    label="Mô tả ngắn"
                                                    :error-messages="errors.collect('Mô tả ngắn', 'frmAddEdit')"
                                                    v-validate="''"
                                                    data-vv-scope="frmAddEdit"
                                                    data-vv-name="Mô tả ngắn"
                                                    hide-details>
                                        </v-textarea>
                                    </v-flex>
                                    <v-flex xs12>
                                        <v-checkbox v-model="dichVu.trangThai" label="Trạng thái ẩn hiện"
                                                    :error-messages="errors.collect('Trạng thái ẩn hiện', 'frmAddEdit')"
                                                    v-validate="''"
                                                    data-vv-scope="frmAddEdit"
                                                    data-vv-name="Trạng thái ẩn/hiện"></v-checkbox>
                                    </v-flex>

                                </v-layout>
                            </v-flex>
                            
                            <v-flex xs12>
                                <vue-ckeditor class="mt-1" v-model="dichVu.baiViet"
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
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DichVuApi, { DichVuApiSearchParams } from '@/apiResources/DichVuApi';
    import { DichVu } from '@/models/DichVu';
    import APIS from '@/apisServer';
    import VueCkeditor from '@/components/Commons/VueCkeditor.vue';
    import { HTTP } from '@/http-servicesNew';

import { Media } from '@/models/Media';
import MediaApi from '@/apiResources/MediaApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: { VueCkeditor },
        data() {
            return {
                dialog: false,
                isUpdate: false,
                dichVu: {
                    anhDaiDien: null as any,
                    media: [] as Media[]
                } as DichVu, 
                dichVuID: 0,
                loading: false,
                searchParamsDichVu: {} as DichVuApiSearchParams,
                APIS: APIS,
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
                dsToaNha:[] as any
            }
        },
        watch: {
        },
        mounted() {
        },
        computed: {
            pages(): number {
                return Math.ceil(this.itemsAnh.length / 4);
            }
        },
        methods: {
            show(isUpdate: boolean, item: DichVu) {
                this.dialog = true;
                //this.getDanhSachToaNha();
                this.$validator.errors.clear("frmAddEdit");
                this.isUpdate = isUpdate;
                this.dichVuID = 0;
                if (this.isUpdate !== true) {
                    this.dichVu = {
                        trangThai: true,
                        anhDaiDien: null as any,
                        media: [] as Media[]
                    } as DichVu;
                    this.itemsAnh = this.dichVu.media;
                }
                else {
                    this.dichVuID = item.dichVuID;
                    this.getDataFromApi(this.dichVuID);
                }
            },
            getDataFromApi(id: number): void {
                DichVuApi.detail(id).then(res => {
                    this.dichVu = res;
                    this.itemsAnh = res.media;
                }); 
            },
            hide() {
                this.dialog = false;
            },
            ganAnhDaiDien(link: any) {
                this.dichVu.anhDaiDien = link;
            },
            deleteMedia(media: Media) {
                var index = (this.dichVu.media as Media[]).indexOf(media);
                if (index > -1) {
                    (this.dichVu.media as Media[]).splice(index, 1);
                }
                if (media.mediaID === 0 || media.mediaID === null || media.mediaID === undefined) {

                }
                else {
                    MediaApi.delete(media.mediaID).then(res => { });
                }
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                    if (res) {
                        this.dichVu.datDichVu = undefined;
                        if (this.isUpdate) {
                            this.loading = true;
                            DichVuApi.update(this.dichVu.dichVuID, this.dichVu).then(res => {
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
                            DichVuApi.insert(this.dichVu).then(res => {
                                this.dialog = false;
                                this.$emit("getData");
                                this.dichVuID = res.dichVuID;
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
            handleImageAdded(file: any, Editor: any, cursorLocation: any) {
                var formData = new FormData()
                var re = /^\w+\.(?:jpg|png|gif|jpeg)$/g
                if (re.test(file.name)) {
                    formData.append('image', file)
                    HTTP.post('/api/img', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                        .then(res => {
                            // console.log(res)
                            let url = res.data
                            Editor.insertEmbed(cursorLocation, 'image', url)
                        })
                } else {
                    this.$snotify.error('Tên file chỉ chứa ký tự chữ, số và dấu "_"');
                }
            },
            changeImg() {
                var formData = new FormData()
                let files = (document as any).querySelector('#File').files[0]
                var re = /^\w+\.(?:jpg|png|gif|jpeg)$/g
                if (re.test(files.name)) {
                    formData.append('img', files)
                    HTTP.post('/api/img', formData, { headers: { 'Content-Type': 'multipart/form-data' } })
                        .then(res => {
                            this.dichVu.anhDaiDien = res.data;
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
                            if (this.dichVuID === 0 || this.dichVuID === null || this.dichVuID === undefined) {

                            }
                            else
                                media.dichVuID = this.dichVuID;
                            (this.dichVu.media as Media[]).push(media);

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
        left: 0px!important;
        top: -10px!important;
    }
    .theme--light.v-pagination .v-pagination__item {
        display: none!important;
        background: #fff;
        color: #000;
        width: auto;
        min-width: 34px;
        padding: 0 5px;
    }
    .v-responsive {
        cursor: pointer!important;
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

