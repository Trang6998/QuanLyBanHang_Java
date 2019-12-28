<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/thuoctinhsanpham" exact>ThuocTinhSanPham</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="thuocTinhSanPham.SanPhamID"
                            :items="dsSanPham"
                            :loading="dsSanPhamLoading"
                            :search-input.sync="searchSanPham"
                            item-text="SanPhamID"
                            item-value="SanPhamID"
                            label="SanPhamID"
                            placeholder="Nhập SanPhamID"
                            :error-messages="errors.collect('SanPhamID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="SanPhamID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="thuocTinhSanPham.ThuocTinhID"
                            :items="dsThuocTinh"
                            :loading="dsThuocTinhLoading"
                            :search-input.sync="searchThuocTinh"
                            item-text="ThuocTinhID"
                            item-value="ThuocTinhID"
                            label="ThuocTinhID"
                            placeholder="Nhập ThuocTinhID"
                            :error-messages="errors.collect('ThuocTinhID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="ThuocTinhID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="thuocTinhSanPham.NoiDungMoTa"
                            label="NoiDungMoTa"
                            type="text"
                            :error-messages="errors.collect('NoiDungMoTa', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="NoiDungMoTa"
                            hide-details
                            clearable
                            ></v-text-field>
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
    import ThuocTinhSanPhamApi, { ThuocTinhSanPhamApiSearchParams } from '@/apiResources/ThuocTinhSanPhamApi';
    import { ThuocTinhSanPham } from '@/models/ThuocTinhSanPham';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                thuocTinhSanPham: {} as ThuocTinhSanPham,
                loading: false,
                searchParamsThuocTinhSanPham: {} as ThuocTinhSanPhamApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaThuocTinhSanPham') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                ThuocTinhSanPhamApi.detail(id).then(res => {
                    this.thuocTinhSanPham = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.thuocTinhSanPham.sanPham = undefined;
                        this.thuocTinhSanPham.thuocTinh = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            ThuocTinhSanPhamApi.update(id, this.thuocTinhSanPham).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            ThuocTinhSanPhamApi.insert(this.thuocTinhSanPham).then(res => {
                                this.$router.push('/thuoctinhsanpham/' + res.thuocTinhSanPhamID);
                                this.thuocTinhSanPham = res;
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
        }
    });
</script>

