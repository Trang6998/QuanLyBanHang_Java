<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/users" exact>Users</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.PasswordSalt"
                            label="PasswordSalt"
                            type="text"
                            :error-messages="errors.collect('PasswordSalt', 'frmAddEdit')"
                            v-validate="'required'"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="PasswordSalt"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-checkbox v-model="users.Active" label="Active"
                            :error-messages="errors.collect('Active', 'frmAddEdit')"
                            v-validate="'required'"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="Active"
                            ></v-checkbox>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.UserName"
                            label="UserName"
                            type="text"
                            :error-messages="errors.collect('UserName', 'frmAddEdit')"
                            v-validate="'required'"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="UserName"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.Email"
                            label="Email"
                            type="text"
                            :error-messages="errors.collect('Email', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="Email"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="users.HoGiaDinhId"
                            :items="dsHoGiaDinh"
                            :loading="dsHoGiaDinhLoading"
                            :search-input.sync="searchHoGiaDinh"
                            item-text="HoGiaDinhId"
                            item-value="HoGiaDinhId"
                            label="HoGiaDinhId"
                            placeholder="Nhập HoGiaDinhId"
                            :error-messages="errors.collect('HoGiaDinhId', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="HoGiaDinhId"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="users.ToaNhaId"
                            :items="dsToaNha"
                            :loading="dsToaNhaLoading"
                            :search-input.sync="searchToaNha"
                            item-text="ToaNhaId"
                            item-value="ToaNhaId"
                            label="ToaNhaId"
                            placeholder="Nhập ToaNhaId"
                            :error-messages="errors.collect('ToaNhaId', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="ToaNhaId"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="users.LoaiTaiKhoanId"
                            :items="dsLoaiTaiKhoan"
                            :loading="dsLoaiTaiKhoanLoading"
                            :search-input.sync="searchLoaiTaiKhoan"
                            item-text="LoaiTaiKhoanId"
                            item-value="LoaiTaiKhoanId"
                            label="LoaiTaiKhoanId"
                            placeholder="Nhập LoaiTaiKhoanId"
                            :error-messages="errors.collect('LoaiTaiKhoanId', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="LoaiTaiKhoanId"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="users.PhongId"
                            :items="dsPhong"
                            :loading="dsPhongLoading"
                            :search-input.sync="searchPhong"
                            item-text="PhongId"
                            item-value="PhongId"
                            label="PhongId"
                            placeholder="Nhập PhongId"
                            :error-messages="errors.collect('PhongId', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="PhongId"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.Password"
                            label="Password"
                            type="text"
                            :error-messages="errors.collect('Password', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="Password"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.CreatedTime"
                            label="CreatedTime"
                            type="datetime-local"
                            :error-messages="errors.collect('CreatedTime', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="CreatedTime"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.LastLogin"
                            label="LastLogin"
                            type="datetime-local"
                            :error-messages="errors.collect('LastLogin', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="LastLogin"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="users.Lang"
                            label="Lang"
                            type="text"
                            :error-messages="errors.collect('Lang', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="Lang"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                        <v-flex xs12>
                            <v-tabs color="primary" dark slider-color="white">
                                <v-tab :key="1" ripple>
                                    AccessTokens
                                </v-tab>
                                <v-tab-item :key="1">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/accesstokens/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderAccessTokens"
                              :items="dsAccessTokens"
                              :pagination.sync="searchParamsAccessTokens"
                              :total-items="searchParamsAccessTokens.totalItems"
                              :loading="dsAccessTokensLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.AccessTokenId }}</td>
                <td>{{ props.item.Token }}</td>
                <td>{{ props.item.EffectiveTime }}</td>
                <td>{{ props.item.ExpiresIn }}</td>
                <td>{{ props.item.UserAgent }}</td>
                <td>{{ props.item.IP }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="2" ripple>
                                    ChangePasswords
                                </v-tab>
                                <v-tab-item :key="2">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/changepasswords/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderChangePasswords"
                              :items="dsChangePasswords"
                              :pagination.sync="searchParamsChangePasswords"
                              :total-items="searchParamsChangePasswords.totalItems"
                              :loading="dsChangePasswordsLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.ChangePasswordId }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
                <td>{{ props.item.ChangePasswordKey }}</td>
                        <td>{{ props.item.Exprire === null ? "" : props.item.Exprire|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="3" ripple>
                                    DatDichVu
                                </v-tab>
                                <v-tab-item :key="3">
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
                        <td>{{ props.item.Users.UserId }}</td>
                        <td>{{ props.item.Users.DichVuID }}</td>
                        <td>{{ props.item.NgayDat === null ? "" : props.item.NgayDat|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                <td>{{ props.item.GhiChu }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="4" ripple>
                                    DonDatHang
                                </v-tab>
                                <v-tab-item :key="4">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/dondathang/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderDonDatHang"
                              :items="dsDonDatHang"
                              :pagination.sync="searchParamsDonDatHang"
                              :total-items="searchParamsDonDatHang.totalItems"
                              :loading="dsDonDatHangLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.DonDatHangID }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
                <td>{{ props.item.SoHieuDon }}</td>
                        <td>{{ props.item.NgayDat === null ? "" : props.item.NgayDat|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenLayTu === null ? "" : props.item.HenLayTu|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenLayDen === null ? "" : props.item.HenLayDen|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenTraTu === null ? "" : props.item.HenTraTu|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenTraDen === null ? "" : props.item.HenTraDen|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                <td>{{ props.item.LaTre ? "Yes" : "No" }}</td>
                <td>{{ props.item.LyDoTraTre }}</td>
                        <td>{{ props.item.NgayGioTraThucTe === null ? "" : props.item.NgayGioTraThucTe|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                <td>{{ props.item.GhiChu }}</td>
                <td>{{ props.item.TinhTrang }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="5" ripple>
                                    DonDatHang
                                </v-tab>
                                <v-tab-item :key="5">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/dondathang/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderDonDatHang"
                              :items="dsDonDatHang"
                              :pagination.sync="searchParamsDonDatHang"
                              :total-items="searchParamsDonDatHang.totalItems"
                              :loading="dsDonDatHangLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.DonDatHangID }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
                <td>{{ props.item.SoHieuDon }}</td>
                        <td>{{ props.item.NgayDat === null ? "" : props.item.NgayDat|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenLayTu === null ? "" : props.item.HenLayTu|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenLayDen === null ? "" : props.item.HenLayDen|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenTraTu === null ? "" : props.item.HenTraTu|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                        <td>{{ props.item.HenTraDen === null ? "" : props.item.HenTraDen|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                <td>{{ props.item.LaTre ? "Yes" : "No" }}</td>
                <td>{{ props.item.LyDoTraTre }}</td>
                        <td>{{ props.item.NgayGioTraThucTe === null ? "" : props.item.NgayGioTraThucTe|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                <td>{{ props.item.GhiChu }}</td>
                <td>{{ props.item.TinhTrang }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="6" ripple>
                                    SanPhamNhaCungCap
                                </v-tab>
                                <v-tab-item :key="6">
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
                        <td>{{ props.item.Users.NhaCungCapID }}</td>
                        <td>{{ props.item.Users.SanPhamID }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
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
                                <v-tab :key="7" ripple>
                                    UserApi
                                </v-tab>
                                <v-tab-item :key="7">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/userapi/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderUserApi"
                              :items="dsUserApi"
                              :pagination.sync="searchParamsUserApi"
                              :total-items="searchParamsUserApi.totalItems"
                              :loading="dsUserApiLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                        <td>{{ props.item.Users.UserId }}</td>
                        <td>{{ props.item.Users.ApiId }}</td>
                <td>{{ props.item.UserApiId }}</td>
                        </template>
                    </v-data-table>
                                        </v-card-text>
                                    </v-card>
                                </v-tab-item>
                                <v-tab :key="8" ripple>
                                    UserGroup
                                </v-tab>
                                <v-tab-item :key="8">
                                    <v-card flat>
                                            <div class="text-xs-right">
                                                <v-btn color="info" to="/usergroup/add" small>Thêm mới</v-btn>
                                            </div>
                                        <v-card-text class="pa-0">
                <v-data-table :headers="tableHeaderUserGroup"
                              :items="dsUserGroup"
                              :pagination.sync="searchParamsUserGroup"
                              :total-items="searchParamsUserGroup.totalItems"
                              :loading="dsUserGroupLoading"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                <td>{{ props.item.UserGroupId }}</td>
                        <td>{{ props.item.Users.GroupId }}</td>
                        <td>{{ props.item.Users.UserId }}</td>
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
    import UsersApi, { UsersApiSearchParams } from '@/apiResources/UsersApi';
    import { Users } from '@/models/Users';
    import { DatDichVu } from '@/models/DatDichVu';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';
    import { DonDatHang } from '@/models/DonDatHang';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
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
                users: {} as Users,
                dsAccessTokensLoading: false,
                searchAccessTokens: '',
                tableHeaderAccessTokens: [
                    { text: 'AccessTokenId', value: 'AccessTokenId', align: 'center', sortable: true },
                    { text: 'Token', value: 'Token', align: 'center', sortable: true },
                    { text: 'UserName', value: 'UserName', align: 'center', sortable: true },
                    { text: 'EffectiveTime', value: 'EffectiveTime', align: 'center', sortable: true },
                    { text: 'ExpiresIn', value: 'ExpiresIn', align: 'center', sortable: true },
                    { text: 'UserAgent', value: 'UserAgent', align: 'center', sortable: true },
                    { text: 'IP', value: 'IP', align: 'center', sortable: true },
                ],
                dsChangePasswordsLoading: false,
                searchChangePasswords: '',
                tableHeaderChangePasswords: [
                    { text: 'ChangePasswordId', value: 'ChangePasswordId', align: 'center', sortable: true },
                    { text: 'UserId', value: 'UserId', align: 'center', sortable: true },
                    { text: 'ChangePasswordKey', value: 'ChangePasswordKey', align: 'center', sortable: true },
                    { text: 'Exprire', value: 'Exprire', align: 'center', sortable: true },
                ],
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
                dsDonDatHang: [] as DonDatHang[],
                dsDonDatHangLoading: false,
                searchDonDatHang: '',
                searchParamsDonDatHang: { includeEntities: true } as DonDatHangApiSearchParams,
                tableHeaderDonDatHang: [
                    { text: 'DonDatHangID', value: 'DonDatHangID', align: 'center', sortable: true },
                    { text: 'TaiKhoanDatHangID', value: 'TaiKhoanDatHangID', align: 'center', sortable: true },
                    { text: 'TaiKhoanNhanVienID', value: 'TaiKhoanNhanVienID', align: 'center', sortable: true },
                    { text: 'SoHieuDon', value: 'SoHieuDon', align: 'center', sortable: true },
                    { text: 'NgayDat', value: 'NgayDat', align: 'center', sortable: true },
                    { text: 'HenLayTu', value: 'HenLayTu', align: 'center', sortable: true },
                    { text: 'HenLayDen', value: 'HenLayDen', align: 'center', sortable: true },
                    { text: 'HenTraTu', value: 'HenTraTu', align: 'center', sortable: true },
                    { text: 'HenTraDen', value: 'HenTraDen', align: 'center', sortable: true },
                    { text: 'LaTre', value: 'LaTre', align: 'center', sortable: true },
                    { text: 'LyDoTraTre', value: 'LyDoTraTre', align: 'center', sortable: true },
                    { text: 'NgayGioTraThucTe', value: 'NgayGioTraThucTe', align: 'center', sortable: true },
                    { text: 'GhiChu', value: 'GhiChu', align: 'center', sortable: true },
                    { text: 'TinhTrang', value: 'TinhTrang', align: 'center', sortable: true },
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
                searchParamsUsers: {} as UsersApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaUsers') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                UsersApi.detail(id).then(res => {
                    this.users = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.users.datDichVu = undefined;
                        this.users.donDatHang1 = undefined;
                        this.users.donDatHang = undefined;
                        this.users.sanPhamNhaCungCap = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            UsersApi.update(id, this.users).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            UsersApi.insert(this.users).then(res => {
                                this.$router.push('/users/' + res.userId);
                                this.users = res;
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
            getDSDonDatHang(): void {
                DonDatHangApi.search(this.searchParamsDonDatHang).then(res => {
                    this.dsDonDatHang = res.data;
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

