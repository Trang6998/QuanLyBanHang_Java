<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/thuoctinh" exact>ThuocTinh</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="thuocTinh.TenThuocTinh"
                            label="TenThuocTinh"
                            type="text"
                            :error-messages="errors.collect('TenThuocTinh', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TenThuocTinh"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="thuocTinh.GhiChu"
                            label="GhiChu"
                            type="text"
                            :error-messages="errors.collect('GhiChu', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="GhiChu"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs12>
                            <v-tabs color="primary" dark slider-color="white">
                                <v-tab :key="1" ripple>
                                    ThuocTinhSanPham
                                </v-tab>
                                <v-tab-item :key="1">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/thuoctinhsanpham/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderThuocTinhSanPham"
                              :items="dsThuocTinhSanPham"
                              :pagination.sync="searchParamsThuocTinhSanPham"
                              :total-items="searchParamsThuocTinhSanPham.totalItems"
                              :loading="dsThuocTinhSanPhamLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.ThuocTinhSanPhamID }}</td>
                        <td>{{ props.item.ThuocTinh.SanPhamID }}</td>
                        <td>{{ props.item.ThuocTinh.ThuocTinhID }}</td>
                <td>{{ props.item.NoiDungMoTa }}</td>
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
    import ThuocTinhApi, { ThuocTinhApiSearchParams } from '@/apiResources/ThuocTinhApi';
    import { ThuocTinh } from '@/models/ThuocTinh';
    import { ThuocTinhSanPham } from '@/models/ThuocTinhSanPham';
    import ThuocTinhSanPhamApi, { ThuocTinhSanPhamApiSearchParams } from '@/apiResources/ThuocTinhSanPhamApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                thuocTinh: {} as ThuocTinh,
                dsThuocTinhSanPham: [] as ThuocTinhSanPham[],
                dsThuocTinhSanPhamLoading: false,
                searchThuocTinhSanPham: '',
                searchParamsThuocTinhSanPham: { includeEntities: true } as ThuocTinhSanPhamApiSearchParams,
                tableHeaderThuocTinhSanPham: [
                    { text: 'ThuocTinhSanPhamID', value: 'ThuocTinhSanPhamID', align: 'center', sortable: true },
                    { text: 'SanPhamID', value: 'SanPhamID', align: 'center', sortable: true },
                    { text: 'ThuocTinhID', value: 'ThuocTinhID', align: 'center', sortable: true },
                    { text: 'NoiDungMoTa', value: 'NoiDungMoTa', align: 'center', sortable: true },
                ],
                loading: false,
                searchParamsThuocTinh: {} as ThuocTinhApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaThuocTinh') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                ThuocTinhApi.detail(id).then(res => {
                    this.thuocTinh = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.thuocTinh.thuocTinhSanPham = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            ThuocTinhApi.update(id, this.thuocTinh).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            ThuocTinhApi.insert(this.thuocTinh).then(res => {
                                this.$router.push('/thuoctinh/' + res.thuocTinhID);
                                this.thuocTinh = res;
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
            getDSThuocTinhSanPham(): void {
                ThuocTinhSanPhamApi.search(this.searchParamsThuocTinhSanPham).then(res => {
                    this.dsThuocTinhSanPham = res.data;
                });
            },
        }
    });
</script>

