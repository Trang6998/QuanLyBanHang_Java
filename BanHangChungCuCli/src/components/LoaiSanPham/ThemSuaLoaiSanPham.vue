<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/loaisanpham" exact>LoaiSanPham</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="loaiSanPham.TenLoai"
                            label="TenLoai"
                            type="text"
                            :error-messages="errors.collect('TenLoai', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TenLoai"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="loaiSanPham.MoTa"
                            label="MoTa"
                            type="text"
                            :error-messages="errors.collect('MoTa', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="MoTa"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs12>
                            <v-tabs color="primary" dark slider-color="white">
                                <v-tab :key="1" ripple>
                                    LoaiSanPham
                                </v-tab>
                                <v-tab-item :key="1">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/loaisanpham/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderLoaiSanPham"
                              :items="dsLoaiSanPham"
                              :pagination.sync="searchParamsLoaiSanPham"
                              :total-items="searchParamsLoaiSanPham.totalItems"
                              :loading="dsLoaiSanPhamLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.LoaiSanPhamID }}</td>
                        <td>{{ props.item.LoaiSanPham.LoaiSanPhamID }}</td>
                <td>{{ props.item.TenLoai }}</td>
                <td>{{ props.item.MoTa }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="2" ripple>
                                    SanPham
                                </v-tab>
                                <v-tab-item :key="2">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/sanpham/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderSanPham"
                              :items="dsSanPham"
                              :pagination.sync="searchParamsSanPham"
                              :total-items="searchParamsSanPham.totalItems"
                              :loading="dsSanPhamLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.SanPhamID }}</td>
                        <td>{{ props.item.LoaiSanPham.LoaiSanPhamID }}</td>
                        <td>{{ props.item.LoaiSanPham.DonViTinhID }}</td>
                <td>{{ props.item.KyHieuSanPham }}</td>
                <td>{{ props.item.TenSanPham }}</td>
                <td>{{ props.item.AnhSanPham }}</td>
                <td>{{ props.item.MoTaNgan }}</td>
                <td>{{ props.item.MoTa }}</td>
                <td>{{ props.item.NhaSanXuat }}</td>
                <td>{{ props.item.AnHienSanPham ? "Yes" : "No" }}</td>
                <td>{{ props.item.AnHienNhaSanXuat ? "Yes" : "No" }}</td>
                <td>{{ props.item.GiaBan }}</td>
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
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { LoaiSanPham } from '@/models/LoaiSanPham';
    import { SanPham } from '@/models/SanPham';
    import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                loaiSanPham: {} as LoaiSanPham,
                dsLoaiSanPham: [] as LoaiSanPham[],
                dsLoaiSanPhamLoading: false,
                searchLoaiSanPham: '',
                searchParamsLoaiSanPham: { includeEntities: true } as LoaiSanPhamApiSearchParams,
                tableHeaderLoaiSanPham: [
                    { text: 'LoaiSanPhamID', value: 'LoaiSanPhamID', align: 'center', sortable: true },
                    { text: 'LoaiSanPhamPID', value: 'LoaiSanPham.LoaiSanPhamID', align: 'center', sortable: true },
                    { text: 'TenLoai', value: 'TenLoai', align: 'center', sortable: true },
                    { text: 'MoTa', value: 'MoTa', align: 'center', sortable: true },
                ],
                dsSanPham: [] as SanPham[],
                dsSanPhamLoading: false,
                searchSanPham: '',
                searchParamsSanPham: { includeEntities: true } as SanPhamApiSearchParams,
                tableHeaderSanPham: [
                    { text: 'SanPhamID', value: 'SanPhamID', align: 'center', sortable: true },
                    { text: 'LoaiSanPhamID', value: 'LoaiSanPhamID', align: 'center', sortable: true },
                    { text: 'DonViTinhID', value: 'DonViTinhID', align: 'center', sortable: true },
                    { text: 'KyHieuSanPham', value: 'KyHieuSanPham', align: 'center', sortable: true },
                    { text: 'TenSanPham', value: 'TenSanPham', align: 'center', sortable: true },
                    { text: 'AnhSanPham', value: 'AnhSanPham', align: 'center', sortable: true },
                    { text: 'MoTaNgan', value: 'MoTaNgan', align: 'center', sortable: true },
                    { text: 'MoTa', value: 'MoTa', align: 'center', sortable: true },
                    { text: 'NhaSanXuat', value: 'NhaSanXuat', align: 'center', sortable: true },
                    { text: 'AnHienSanPham', value: 'AnHienSanPham', align: 'center', sortable: true },
                    { text: 'AnHienNhaSanXuat', value: 'AnHienNhaSanXuat', align: 'center', sortable: true },
                    { text: 'GiaBan', value: 'GiaBan', align: 'center', sortable: true },
                ],
                loading: false,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaLoaiSanPham') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                LoaiSanPhamApi.detail(id).then(res => {
                    this.loaiSanPham = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.loaiSanPham.loaiSanPham1 = undefined;
                        this.loaiSanPham.loaiSanPham2 = undefined;
                        this.loaiSanPham.sanPham = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            LoaiSanPhamApi.update(id, this.loaiSanPham).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            LoaiSanPhamApi.insert(this.loaiSanPham).then(res => {
                                this.$router.push('/loaisanpham/' + res.loaiSanPhamID);
                                this.loaiSanPham = res;
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
            getDSLoaiSanPham(): void {
                LoaiSanPhamApi.search(this.searchParamsLoaiSanPham).then(res => {
                    this.dsLoaiSanPham = res.data;
                });
            },
            getDSSanPham(): void {
                SanPhamApi.search(this.searchParamsSanPham).then(res => {
                    this.dsSanPham = res.data;
                });
            },
        }
    });
</script>

