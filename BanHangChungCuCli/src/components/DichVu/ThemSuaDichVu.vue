<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/dichvu" exact>DichVu</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="dichVu.TenDichVu"
                            label="TenDichVu"
                            type="text"
                            :error-messages="errors.collect('TenDichVu', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TenDichVu"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="dichVu.AnhDaiDien"
                            label="AnhDaiDien"
                            type="text" 
                            :error-messages="errors.collect('AnhDaiDien', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="AnhDaiDien"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="dichVu.MoTaNgan"
                            label="MoTaNgan"
                            type="text"
                            :error-messages="errors.collect('MoTaNgan', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="MoTaNgan"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-checkbox v-model="dichVu.TrangThai" label="TrangThai"
                            :error-messages="errors.collect('TrangThai', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TrangThai"
                            ></v-checkbox>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="dichVu.BaiViet"
                            label="BaiViet"
                            type="text"
                            :error-messages="errors.collect('BaiViet', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="BaiViet"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs12>
                            <v-tabs color="primary" dark slider-color="white">
                                <v-tab :key="1" ripple>
                                    DatDichVu
                                </v-tab>
                                <v-tab-item :key="1">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/datdichvu/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                                            <v-data-table :headers="tableHeaderDatDichVu"
                                                          :items="dsDatDichVu"
                                                          :pagination.sync="searchParamsDatDichVu"
                                                          :total-items="searchParamsDatDichVu.totalItems"
                                                          :loading="dsDatDichVuLoading"
                                                          class="table-border table">
                                                <template slot="items" slot-scope="props">
                                                    <td>{{ props.item.DatDichVuID }}</td>
                                                    <td>{{ props.item.DichVu.UserId }}</td>
                                                    <td>{{ props.item.DichVu.DichVuID }}</td>
                                                    <td>{{ props.item.NgayDat === null ? "" : props.item.NgayDat|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                                                    <td>{{ props.item.GhiChu }}</td>
                                                    </template>
                                            </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="2" ripple>
                                    Media
                                </v-tab>
                                <v-tab-item :key="2">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/media/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                                            <v-data-table :headers="tableHeaderMedia"
                                                          :items="dsMedia"
                                                          :pagination.sync="searchParamsMedia"
                                                          :total-items="searchParamsMedia.totalItems"
                                                          :loading="dsMediaLoading"
                                                          class="table-border table">
                                                <template slot="items" slot-scope="props">
                                                    <td>{{ props.item.MediaID }}</td>
                                                    <td>{{ props.item.DichVu.DichVuID }}</td>
                                                    <td>{{ props.item.DichVu.SanPhamID }}</td>
                                                    <td>{{ props.item.LaAnh }}</td>
                                                    <td>{{ props.item.DuongLink }}</td>
                                                    <td>{{ props.item.MoTa }}</td>
                                                </template>
                                            </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                            </v-tabs>
                        </v-flex>

                    </v-layout>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn class="primary" :disabled="loading" :loading="loading" @click.native="save">{{isUpdate?'Cập nhật':'Thêm mới'}}</v-btn>
            </v-card-actions>
        </v-card>
    </v-flex>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DichVuApi, { DichVuApiSearchParams } from '@/apiResources/DichVuApi';
    import { DichVu } from '@/models/DichVu';
    import { DatDichVu } from '@/models/DatDichVu';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';
    import { Media } from '@/models/Media';
    import MediaApi, { MediaApiSearchParams } from '@/apiResources/MediaApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                dichVu: {} as DichVu,
                dsDatDichVu: [] as DatDichVu[],
                dsDatDichVuLoading: false,
                searchDatDichVu: '',
                searchParamsDatDichVu: { includeEntities: true } as DatDichVuApiSearchParams,
                tableHeaderDatDichVu: [
                    { text: 'DatDichVuID', value: 'DatDichVuID', align: 'center', sortable: true },
                    { text: 'UserID', value: 'UserID', align: 'center', sortable: true },
                    { text: 'DichVuID', value: 'DichVuID', align: 'center', sortable: true },
                    { text: 'NgayDat', value: 'NgayDat', align: 'center', sortable: true },
                    { text: 'GhiChu', value: 'GhiChu', align: 'center', sortable: true },
                ],
                dsMedia: [] as Media[],
                dsMediaLoading: false,
                searchMedia: '',
                searchParamsMedia: { includeEntities: true } as MediaApiSearchParams,
                tableHeaderMedia: [
                    { text: 'MediaID', value: 'MediaID', align: 'center', sortable: true },
                    { text: 'DichVuID', value: 'DichVuID', align: 'center', sortable: true },
                    { text: 'SanPhamID', value: 'SanPhamID', align: 'center', sortable: true },
                    { text: 'LaAnh', value: 'LaAnh', align: 'center', sortable: true },
                    { text: 'DuongLink', value: 'DuongLink', align: 'center', sortable: true },
                    { text: 'MoTa', value: 'MoTa', align: 'center', sortable: true },
                ],
                loading: false,
                searchParamsDichVu: {} as DichVuApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaDichVu') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                DichVuApi.detail(id).then(res => {
                    this.dichVu = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.dichVu.datDichVu = undefined;
                        this.dichVu.media = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            DichVuApi.update(id, this.dichVu).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            DichVuApi.insert(this.dichVu).then(res => {
                                this.$router.push('/dichvu/' + res.dichVuID);
                                this.dichVu = res;
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
            getDSDatDichVu(): void {
                DatDichVuApi.search(this.searchParamsDatDichVu).then(res => {
                    this.dsDatDichVu = res.data;
                });
            },
            getDSMedia(): void {
                MediaApi.search(this.searchParamsMedia).then(res => {
                    this.dsMedia = res.data;
                });
            },
        }
    });
</script>

