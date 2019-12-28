<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <!--<v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>-->
            <v-breadcrumbs-item to="/danh-muc-san-pham" exact>Danh mục</v-breadcrumbs-item>
            <v-breadcrumbs-item v-if="tenLoaiCha!=null" :to="'/danh-muc-san-pham/' + danhMucChaID">
                {{tenLoaiCha}}
            </v-breadcrumbs-item>
            <v-breadcrumbs-item>
                <a @click="getDSSanPham(idDanhMucCha)">
                    {{tenDanhMuc}}
                </a>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item v-if="toiDanhMucCon != null">{{toiDanhMucCon}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-container style="overflow-x: scroll; padding: 15px; padding-bottom: 5px; width:100%">
            <v-layout style="font-size:10px">
                <template v-for="(item, index) in dsLoaiSanPham">
                    <v-flex :key="index" :class="item.active? 'active' : ''" style="height: 100%;cursor: pointer; text-align: center; max-width: 107px" @click="getDSSanPham(item.loaiSanPhamID, index)">
                        <v-avatar style="margin-top: 10px">
                            <v-img v-if="item.anhDaiDien != null" slot="activator"
                                   :src="APIS.HOST + 'fileupload/download?key=' + item.anhDaiDien"
                                   style="width: 60px!important; height: 60px!important" :id="'img2_' + item.loaiSanPhamID"
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
                                   style="width: 60px!important; height: 60px!important" id="img"
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
                        </v-avatar>

                        <v-card-text pa-0 ma-0 style="padding:10px 0px; padding-bottom: 5px">
                            <div class="pa-0 ma-0 text-xs-center" style="font-size: 14px;">{{ item.tenLoai }}</div>
                        </v-card-text>
                    </v-flex>
                </template>
            </v-layout>

        </v-container>
        <v-layout row wrap>
            <v-flex xs12 md4 style="margin-top: 5px">
                <v-text-field label="Tìm kiếm sản phẩm" clearable v-model="searchParamsSanPham.tenSanPham" @input="getDataFromApi(searchParamsSanPham)" hide-details></v-text-field>
            </v-flex>
            <v-flex xs12 md8 style="display: inline-flex;margin-top: 5px">
                <v-layout nowrap>
                    <v-flex xs6 sm3>
                        <v-text-field label="Giá từ" type="number" min="0" suffix="VNĐ" v-model="searchParamsSanPham.toiThieu" @input="getDataFromApi(searchParamsSanPham)"></v-text-field>
                    </v-flex>
                    <v-flex xs6 sm3>
                        <v-text-field label="Giá đến" type="number" min="0" suffix="VNĐ" v-model="searchParamsSanPham.toiDa" @input="getDataFromApi(searchParamsSanPham)"></v-text-field>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>

        <v-layout row wrap>
            <template v-for="item in dsSanPham">
                <v-flex xs6 sm2 style="padding: 6px">
                    <v-card :to="'/chi-tiet-san-pham/' + item.sanPhamID " style="padding: 4px;">
                        <v-card-text ma-0 style="padding: 0px!important" class="zoom">
                            <v-layout row wrap>
                                <v-flex style="padding: 0px!important">
                                    <v-img v-if="item.anhSanPham != null" slot="activator"
                                           :src="APIS.HOST + 'fileupload/download?key=' + item.anhSanPham"
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
                            </v-layout>
                            <div class="MW4BW_" v-if="item.laSanPhamMoi">
                                <div class="_3BQlNg" style="color: rgb(208, 1, 27);">
                                    <div class="_1jRNA3">
                                        <span style="color: white">New</span>
                                    </div>
                                </div>
                            </div>

                        </v-card-text>
                        <v-card-title ma-0 style="padding-bottom: 0px!important; padding-left: 2px!important;">
                            <div style="margin:0px; color: black;" class="ellipsis"><h3 style="font-weight: 400">{{item.tenSanPham}}</h3></div>
                        </v-card-title>
                        <v-card-title ma-0 style="padding-top: 0px!important; padding-left: 2px!important;">
                            <p style="margin:0px; color: red">{{ item.giaBan |currency('', 0) }}&nbsp;<u>đ</u></p>
                        </v-card-title>
                    </v-card>
                </v-flex>

            </template>
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
    import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { SanPham } from '@/models/SanPham';
    import { LoaiSanPham } from '@/models/LoaiSanPham';

    export default Vue.extend({
        components: {},
        data() {
            return {
                isUpdate: false,
                dsSanPham: [] as SanPham[],
                searchParamsSanPham: { includeEntities: true, rowsPerPage: 0
                } as SanPhamApiSearchParams,
                selectedSanPham: {} as SanPham,
                dialogConfirmDelete: false,
                APIS: APIS,
                loaiSanPhamID: 0,
                tenDanhMuc: '',
                idDanhMucCha: 0,
                dsLoaiSanPham: [] as any[],
                searchParamsLoaiSanPham: { includeEntities: true, rowsPerPage: 0 } as LoaiSanPhamApiSearchParams,
                selectedLoaiSanPham: {} as LoaiSanPham,
                toiDanhMucCon: null as any,
                tenLoaiCha: null as any,
                danhMucChaID:0
            }
        },
        mounted() {
            if (this.$route.name === 'DanhSachSanPham') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.idDanhMucCha = id;
                this.searchParamsSanPham.loaiSanPhamID = id;
                this.searchParamsSanPham.trangThaiAnHien = true;
                this.searchParamsLoaiSanPham.loaiSanPhamPID = id;
                this.getTenDanhMuc(id);
                this.getDataFromApi(this.searchParamsSanPham);
            } else {
                this.isUpdate = false;
            }
        },
        watch: {
        },
        created() {
            this.getDataDanhMucFromApi(this.searchParamsLoaiSanPham);

        },
        methods: {
            getTenDanhMuc(id: number) {
                LoaiSanPhamApi.detail(id).then(res => {
                    if (id === this.idDanhMucCha) {
                        this.tenDanhMuc = res.tenLoai;
                        if ((res as any).danhMucCha !== null) {
                            this.tenLoaiCha = (res as any).danhMucCha.tenLoai;
                            this.danhMucChaID = res.loaiSanPhamPID;
                        }
                    }
                    else
                        this.toiDanhMucCon = res.tenLoai;
                });
            },
            getDSSanPham(id: number, index: number) {

                for (let i = 0; i < this.dsLoaiSanPham.length; i++) {
                    if (this.dsLoaiSanPham[i].loaiSanPhamID == id) {
                        this.dsLoaiSanPham[i].active = true;
                    } else {
                        this.dsLoaiSanPham[i].active = false;
                    }
                }
                if (id === this.idDanhMucCha)
                    this.toiDanhMucCon = null;
                else
                    this.getTenDanhMuc(id);
                this.searchParamsSanPham.loaiSanPhamID = id;
                this.getDataFromApi(this.searchParamsSanPham);
            },
            getDataFromApi(searchParamsSanPham: SanPhamApiSearchParams): void {
                searchParamsSanPham.layCaDanhMucCon = true;
                SanPhamApi.search(searchParamsSanPham).then(res => {
                    this.dsSanPham = res.data;
                });
            },
            getDataDanhMucFromApi(searchParamsLoaiSanPham: LoaiSanPhamApiSearchParams): void {
                LoaiSanPhamApi.search(searchParamsLoaiSanPham).then(res => {
                    this.dsLoaiSanPham = res.data;
                    this.searchParamsLoaiSanPham.totalItems = res.pagination.totalItems;
                });
            },
            toggle(index: number) {
                this.dsLoaiSanPham[index].active = true;
            }
        }
    });
</script>
<style>
    .zoom :hover {
        margin-top: -3px !important;
    }

    .new {
        background-color: rgb(208, 1, 27);
        color: white;
        padding: 0px 5px;
        position: relative;
        left: -0.5rem;
        top: -11.375rem;
    }

    .MW4BW_ {
        position: absolute;
        left: 0;
        top: .625rem;
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -webkit-flex-direction: column;
        -moz-box-orient: vertical;
        -moz-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
        -webkit-box-align: start;
        -webkit-align-items: flex-start;
        -moz-box-align: start;
        -ms-flex-align: start;
        align-items: flex-start;
        z-index: 1;
    }

    ._3BQlNg {
        position: relative;
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
        height: 1.5rem;
        padding: 0 3px;
        border-top-right-radius: .1875rem;
        border-bottom-right-radius: .1875rem;
        background: currentColor;
        left: -.25rem;
    }

    ._1jRNA3 {
        background-repeat: no-repeat;
        background-size: contain;
        height: 1.5rem;
        width: 2.75rem;
        position: relative;
        padding: 0px 5px;
    }

    .ellipsis {
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    .active {
        background-color: lemonchiffon;
        border-color: #f6821f !important;
        border: solid;
        border-width: 1px !important;
    }
</style>