<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/dondathang" exact>DonDatHang</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="donDatHang.TaiKhoanDatHangID"
                            :items="dsUsers"
                            :loading="dsUsersLoading"
                            :search-input.sync="searchUsers"
                            item-text="UserId"
                            item-value="UserId"
                            label="TaiKhoanDatHangID"
                            placeholder="Nhập TaiKhoanDatHangID"
                            :error-messages="errors.collect('TaiKhoanDatHangID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TaiKhoanDatHangID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="donDatHang.TaiKhoanNhanVienID"
                            :items="dsUsers"
                            :loading="dsUsersLoading"
                            :search-input.sync="searchUsers"
                            item-text="UserId"
                            item-value="UserId"
                            label="TaiKhoanNhanVienID"
                            placeholder="Nhập TaiKhoanNhanVienID"
                            :error-messages="errors.collect('TaiKhoanNhanVienID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TaiKhoanNhanVienID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.SoHieuDon"
                            label="SoHieuDon"
                            type="text"
                            :error-messages="errors.collect('SoHieuDon', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="SoHieuDon"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.NgayDat"
                            label="NgayDat"
                            type="datetime-local"
                            :error-messages="errors.collect('NgayDat', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="NgayDat"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.HenLayTu"
                            label="HenLayTu"
                            type="datetime-local"
                            :error-messages="errors.collect('HenLayTu', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="HenLayTu"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.HenLayDen"
                            label="HenLayDen"
                            type="datetime-local"
                            :error-messages="errors.collect('HenLayDen', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="HenLayDen"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.HenTraTu"
                            label="HenTraTu"
                            type="datetime-local"
                            :error-messages="errors.collect('HenTraTu', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="HenTraTu"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.HenTraDen"
                            label="HenTraDen"
                            type="datetime-local"
                            :error-messages="errors.collect('HenTraDen', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="HenTraDen"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-checkbox v-model="donDatHang.LaTre" label="LaTre"
                            :error-messages="errors.collect('LaTre', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="LaTre"
                            ></v-checkbox>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.LyDoTraTre"
                            label="LyDoTraTre"
                            type="text"
                            :error-messages="errors.collect('LyDoTraTre', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="LyDoTraTre"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.NgayGioTraThucTe"
                            label="NgayGioTraThucTe"
                            type="datetime-local"
                            :error-messages="errors.collect('NgayGioTraThucTe', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="NgayGioTraThucTe"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.GhiChu"
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

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="donDatHang.TinhTrang"
                            label="TinhTrang"
                            type="number"
                            :error-messages="errors.collect('TinhTrang', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TinhTrang"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-tabs color="primary" dark slider-color="white">
                                <v-tab :key="1" ripple>
                                    ChiTietDonDatHang
                                </v-tab>
                                <v-tab-item :key="1">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/chitietdondathang/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderChiTietDonDatHang"
                              :items="dsChiTietDonDatHang"
                              :pagination.sync="searchParamsChiTietDonDatHang"
                              :total-items="searchParamsChiTietDonDatHang.totalItems"
                              :loading="dsChiTietDonDatHangLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.ChiTietDonDatHangID }}</td>
                        <td>{{ props.item.DonDatHang.DonDatHangID }}</td>
                        <td>{{ props.item.DonDatHang.SanPhamID }}</td>
                <td>{{ props.item.SoLuong }}</td>
                <td>{{ props.item.GiaXuat }}</td>
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
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import { DonDatHang } from '@/models/DonDatHang';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                donDatHang: {} as DonDatHang,
                dsChiTietDonDatHang: [] as ChiTietDonDatHang[],
                dsChiTietDonDatHangLoading: false,
                searchChiTietDonDatHang: '',
                searchParamsChiTietDonDatHang: { includeEntities: true } as ChiTietDonDatHangApiSearchParams,
                tableHeaderChiTietDonDatHang: [
                    { text: 'ChiTietDonDatHangID', value: 'ChiTietDonDatHangID', align: 'center', sortable: true },
                    { text: 'DonDatHangID', value: 'DonDatHangID', align: 'center', sortable: true },
                    { text: 'SanPhamID', value: 'SanPhamID', align: 'center', sortable: true },
                    { text: 'SoLuong', value: 'SoLuong', align: 'center', sortable: true },
                    { text: 'GiaXuat', value: 'GiaXuat', align: 'center', sortable: true },
                ],
                loading: false,
                searchParamsDonDatHang: {} as DonDatHangApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaDonDatHang') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                DonDatHangApi.detail(id).then(res => {
                    this.donDatHang = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.donDatHang.users = undefined;
                        this.donDatHang.users1 = undefined;
                        this.donDatHang.lstChiTietDonDatHang = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            DonDatHangApi.update(id, this.donDatHang).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            DonDatHangApi.insert(this.donDatHang).then(res => {
                                this.$router.push('/dondathang/' + res.donDatHangID);
                                this.donDatHang = res;
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
            getDSChiTietDonDatHang(): void {
                ChiTietDonDatHangApi.search(this.searchParamsChiTietDonDatHang).then(res => {
                    this.dsChiTietDonDatHang = res.data;
                });
            },
        }
    });
</script>

