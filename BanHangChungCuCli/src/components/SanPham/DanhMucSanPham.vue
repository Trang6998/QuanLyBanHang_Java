<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <!--<v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>-->
            <v-breadcrumbs-item to="/danh-muc-san-pham" exact>Danh mục</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-layout row wrap>
            <template v-for="item in dsLoaiSanPham">
                <v-flex xs4 sm2 style="border:0.2px solid #ccc; background: #fff; cursor: pointer">
                    <v-card :to="'/danh-muc-san-pham/' + item.loaiSanPhamID " style="padding: 4px; width: 100%; height: 100%" class="zoom">
                        <v-card-text ma-0 style="padding: 0px!important">
                            <v-layout row wrap>
                                <v-flex style="padding: 0px!important">
                                    <v-img v-if="item.anhDaiDien != null" slot="activator"
                                           :src="APIS.HOST + 'fileupload/download?key=' + item.anhDaiDien"
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
                        </v-card-text>
                        <v-card-title pa-0 ma-0 style="padding-right: 0px!important; padding-left: 0px !important; padding-bottom: 0px!important">
                            <div pa-0 ma-0 style="font-weight: 400">{{ item.tenLoai }}</div>
                        </v-card-title>
                    </v-card>
                </v-flex>
            </template>
        </v-layout>
    </v-flex>
</template>
<script lang="ts">
    import APIS from '@/apisServer';
import { Vue } from 'vue-property-decorator';
import { LoaiSanPham } from '@/models/LoaiSanPham';
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsLoaiSanPham: [] as LoaiSanPham[],
                searchParamsLoaiSanPham: { includeEntities: true, rowsPerPage: 0 } as LoaiSanPhamApiSearchParams,
                selectedLoaiSanPham: {} as LoaiSanPham,
                dialogConfirmDelete: false,
                APIS: APIS
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsLoaiSanPham);
        },
        methods: {
            getDataFromApi(searchParamsLoaiSanPham: LoaiSanPhamApiSearchParams): void {
                searchParamsLoaiSanPham.laDanhMucCha = true;
                LoaiSanPhamApi.search(searchParamsLoaiSanPham).then(res => {
                    this.dsLoaiSanPham = res as any;
                    //this.searchParamsLoaiSanPham.totalItems = res.pagination.totalItems;
                });
            }
        }
    });
</script>


