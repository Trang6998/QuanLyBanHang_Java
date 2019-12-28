<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/sanpham" exact>SanPham</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="sanPham.LoaiSanPhamID"
                            :items="dsLoaiSanPham"
                            :loading="dsLoaiSanPhamLoading"
                            :search-input.sync="searchLoaiSanPham"
                            item-text="LoaiSanPhamID"
                            item-value="LoaiSanPhamID"
                            label="LoaiSanPhamID"
                            placeholder="Nhập LoaiSanPhamID"
                            :error-messages="errors.collect('LoaiSanPhamID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="LoaiSanPhamID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="sanPham.DonViTinhID"
                            :items="dsDonViTinh"
                            :loading="dsDonViTinhLoading"
                            :search-input.sync="searchDonViTinh"
                            item-text="DonViTinhID"
                            item-value="DonViTinhID"
                            label="DonViTinhID"
                            placeholder="Nhập DonViTinhID"
                            :error-messages="errors.collect('DonViTinhID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="DonViTinhID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="sanPham.KyHieuSanPham"
                            label="KyHieuSanPham"
                            type="text"
                            :error-messages="errors.collect('KyHieuSanPham', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="KyHieuSanPham"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="sanPham.TenSanPham"
                            label="TenSanPham"
                            type="text"
                            :error-messages="errors.collect('TenSanPham', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="TenSanPham"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="sanPham.AnhSanPham"
                            label="AnhSanPham"
                            type="text"
                            :error-messages="errors.collect('AnhSanPham', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="AnhSanPham"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="sanPham.MoTaNgan"
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
                            <v-text-field
                            v-model="sanPham.MoTa"
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

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="sanPham.NhaSanXuat"
                            label="NhaSanXuat"
                            type="text"
                            :error-messages="errors.collect('NhaSanXuat', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="NhaSanXuat"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-checkbox v-model="sanPham.AnHienSanPham" label="AnHienSanPham"
                            :error-messages="errors.collect('AnHienSanPham', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="AnHienSanPham"
                            ></v-checkbox>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-checkbox v-model="sanPham.AnHienNhaSanXuat" label="AnHienNhaSanXuat"
                            :error-messages="errors.collect('AnHienNhaSanXuat', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="AnHienNhaSanXuat"
                            ></v-checkbox>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="sanPham.GiaBan"
                            label="GiaBan"
                            type="number"
                            :error-messages="errors.collect('GiaBan', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="GiaBan"
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
                        <td>{{ props.item.SanPham.DonDatHangID }}</td>
                        <td>{{ props.item.SanPham.SanPhamID }}</td>
                <td>{{ props.item.SoLuong }}</td>
                <td>{{ props.item.GiaXuat }}</td>
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
                        <td>{{ props.item.SanPham.DichVuID }}</td>
                        <td>{{ props.item.SanPham.SanPhamID }}</td>
                <td>{{ props.item.LaAnh }}</td>
                <td>{{ props.item.DuongLink }}</td>
                <td>{{ props.item.MoTa }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="3" ripple>
                                    ThuocTinhSanPham
                                </v-tab>
                                <v-tab-item :key="3">
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
                        <td>{{ props.item.SanPham.SanPhamID }}</td>
                        <td>{{ props.item.SanPham.ThuocTinhID }}</td>
                <td>{{ props.item.NoiDungMoTa }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="4" ripple>
                                    SanPhamNhaCungCap
                                </v-tab>
                                <v-tab-item :key="4">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/sanphamnhacungcap/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderSanPhamNhaCungCap"
                              :items="dsSanPhamNhaCungCap"
                              :pagination.sync="searchParamsSanPhamNhaCungCap"
                              :total-items="searchParamsSanPhamNhaCungCap.totalItems"
                              :loading="dsSanPhamNhaCungCapLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.SanPhamNhaCungCapID }}</td>
                        <td>{{ props.item.SanPham.NhaCungCapID }}</td>
                        <td>{{ props.item.SanPham.SanPhamID }}</td>
                        <td>{{ props.item.SanPham.UserId }}</td>
                        <td>{{ props.item.NgayNhap === null ? "" : props.item.NgayNhap|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                <td>{{ props.item.SoLuong }}</td>
                <td>{{ props.item.DonGiaNhap }}</td>
                <td>{{ props.item.GhiChu }}</td>
                <td>{{ props.item.SoThuTuBan }}</td>
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
    import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';
    import { SanPham } from '@/models/SanPham';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
    import { Media } from '@/models/Media';
    import MediaApi, { MediaApiSearchParams } from '@/apiResources/MediaApi';
    import { ThuocTinhSanPham } from '@/models/ThuocTinhSanPham';
    import ThuocTinhSanPhamApi, { ThuocTinhSanPhamApiSearchParams } from '@/apiResources/ThuocTinhSanPhamApi';
    import { SanPhamNhaCungCap } from '@/models/SanPhamNhaCungCap';
    import SanPhamNhaCungCapApi, { SanPhamNhaCungCapApiSearchParams } from '@/apiResources/SanPhamNhaCungCapApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                sanPham: {} as SanPham,
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
                dsSanPhamNhaCungCap: [] as SanPhamNhaCungCap[],
                dsSanPhamNhaCungCapLoading: false,
                searchSanPhamNhaCungCap: '',
                searchParamsSanPhamNhaCungCap: { includeEntities: true } as SanPhamNhaCungCapApiSearchParams,
                tableHeaderSanPhamNhaCungCap: [
                    { text: 'SanPhamNhaCungCapID', value: 'SanPhamNhaCungCapID', align: 'center', sortable: true },
                    { text: 'NhaCungCapID', value: 'NhaCungCapID', align: 'center', sortable: true },
                    { text: 'SanPhamID', value: 'SanPhamID', align: 'center', sortable: true },
                    { text: 'NhanVienID', value: 'NhanVienID', align: 'center', sortable: true },
                    { text: 'NgayNhap', value: 'NgayNhap', align: 'center', sortable: true },
                    { text: 'SoLuong', value: 'SoLuong', align: 'center', sortable: true },
                    { text: 'DonGiaNhap', value: 'DonGiaNhap', align: 'center', sortable: true },
                    { text: 'GhiChu', value: 'GhiChu', align: 'center', sortable: true },
                    { text: 'SoThuTuBan', value: 'SoThuTuBan', align: 'center', sortable: true },
                ],
                loading: false,
                searchParamsSanPham: {} as SanPhamApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaSanPham') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                SanPhamApi.detail(id).then(res => {
                    this.sanPham = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.sanPham.loaiSanPham = undefined;
                        this.sanPham.chiTietDonDatHang = undefined;
                        this.sanPham.media = undefined;
                        this.sanPham.thuocTinhSanPham = undefined;
                        this.sanPham.sanPhamNhaCungCap = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            SanPhamApi.update(id, this.sanPham).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            SanPhamApi.insert(this.sanPham).then(res => {
                                this.$router.push('/sanpham/' + res.sanPhamID);
                                this.sanPham = res;
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
            getDSMedia(): void {
                MediaApi.search(this.searchParamsMedia).then(res => {
                    this.dsMedia = res.data;
                });
            },
            getDSThuocTinhSanPham(): void {
                ThuocTinhSanPhamApi.search(this.searchParamsThuocTinhSanPham).then(res => {
                    this.dsThuocTinhSanPham = res.data;
                });
            },
            getDSSanPhamNhaCungCap(): void {
                SanPhamNhaCungCapApi.search(this.searchParamsSanPhamNhaCungCap).then(res => {
                    this.dsSanPhamNhaCungCap = res.data;
                });
            },
        }
    });
</script>

