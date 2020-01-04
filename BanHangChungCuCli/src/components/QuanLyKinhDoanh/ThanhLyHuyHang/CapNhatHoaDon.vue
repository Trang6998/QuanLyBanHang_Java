<template>
    <v-dialog v-model="dialog" width="700" persistent>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>{{isUpdate ? 'Cập nhật hóa đơn' : 'Thêm mới hóa đơn'}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <v-form scope="frmAddEdit">
                        <v-layout row wrap>
                            <v-flex xs12 sm5>
                                <v-text-field v-model="hoaDon.soHieuDon"
                                              label="Số hóa đơn"
                                              placeholder="SHD0000"
                                              type="text"
                                              :error-messages="errors.collect('Số hóa đơn', 'frmAddEdit')"
                                              v-validate="'required'"
                                              data-vv-scope="frmAddEdit"
                                              data-vv-name="Số hóa đơn"
                                              clearable></v-text-field>
                            </v-flex>
                            <v-flex xs12 sm7>
                                <v-datetimepicker label="Ngày hóa đơn" hide-details v-model="hoaDon.ngayDat"
                                                  :error-messages="errors.collect('Ngày hóa đơn', 'frmAddEdit')"
                                                  v-validate="'required'"
                                                  data-vv-scope="frmAddEdit"
                                                  data-vv-name="Ngày hóa đơn"
                                                  clearable></v-datetimepicker>
                            </v-flex>
                            <v-flex xs12>
                                <v-textarea rows="3" v-model="hoaDon.ghiChu"
                                            label="Ghi chú"
                                             placeholder="Thanh lý/ Hủy hàng/ Trả NCC/ ...."
                                            :error-messages="errors.collect('Ghi chú', 'frmAddEdit')"
                                            v-validate="''"
                                            data-vv-scope="frmAddEdit"
                                            data-vv-name="Ghi chú"
                                            hide-details>
                                </v-textarea>
                            </v-flex>

                            <v-flex xs12 v-if="hoaDon.donDatHangID != null && hoaDon.donDatHangID != 0 && hoaDon.donDatHangID != undefined">
                                <v-layout row wrap>
                                    <v-flex xs12>
                                        <v-form scope="frmAddEdit1">
                                            <v-layout row wrap>
                                                <v-flex xs11>
                                                    <v-layout row wrap>
                                                        <v-flex xs6 md4>
                                                            <v-autocomplete v-model="danhMucID"
                                                                            label="Danh mục"
                                                                            placeholder="Chọn danh mục"
                                                                            :items="dsDanhMuc"
                                                                            item-text="tenLoai"
                                                                            item-value="loaiSanPhamID"
                                                                            @change="getDsSanPham"
                                                                            :error-messages="errors.collect('Danh mục', 'frmAddEdit1')"
                                                                            v-validate="''"
                                                                            data-vv-scope="frmAddEdit1"
                                                                            data-vv-name="Danh mục"
                                                                            clearable>
                                                            </v-autocomplete>
                                                        </v-flex>
                                                        <v-flex xs6 md4>
                                                            <v-autocomplete v-model="chiTietDon.sanPhamID"
                                                                            label="Sản phẩm"
                                                                            placeholder="Chọn sản phẩm"
                                                                            :items="dsSanPham"
                                                                            item-text="tenSanPham"
                                                                            item-value="sanPhamID"
                                                                            :error-messages="errors.collect('Sản phẩm', 'frmAddEdit1')"
                                                                            v-validate="'required'"
                                                                            data-vv-scope="frmAddEdit1"
                                                                            data-vv-name="Sản phẩm"
                                                                            clearable>
                                                            </v-autocomplete>
                                                        </v-flex>
                                                        <v-flex xs6 md2>
                                                            <v-text-field v-model="chiTietDon.soLuong"
                                                                          label="Số lượng"
                                                                          placeholder="5"
                                                                          type="text"
                                                                          :error-messages="errors.collect('Số lượng', 'frmAddEdit1')"
                                                                          v-validate="'required'"
                                                                          data-vv-scope="frmAddEdit1"
                                                                          data-vv-name="Số lượng"
                                                                          ></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs6 md2>
                                                            <v-text-field v-model="chiTietDon.giaXuat"
                                                                          label="Giá xuất"
                                                                          placeholder="0"
                                                                          type="text"
                                                                          :error-messages="errors.collect('Giá xuất', 'frmAddEdit1')"
                                                                          v-validate="'required'"
                                                                          data-vv-scope="frmAddEdit1"
                                                                          data-vv-name="Giá xuất"
                                                                          ></v-text-field>
                                                        </v-flex>
                                                    </v-layout>
                                                </v-flex>
                                                <v-flex xs1>
                                                    <v-btn ma-0 flat fab color="green" @click="saveChiTietDon">
                                                        <v-icon>
                                                            done
                                                        </v-icon>
                                                    </v-btn>
                                                </v-flex>
                                            </v-layout>
                                        </v-form>
                                    </v-flex>
                                    <v-flex xs12>
                                        <v-data-table :headers="tableHeader"
                                                      :items="dsChiTietDonDatHang"
                                                      @update:pagination="getChiTietDon" :pagination.sync="searchParamsChiTietDonDatHang"
                                                      :total-items="searchParamsChiTietDonDatHang.totalItems"
                                                      :loading="loadingTable" hide-actions
                                                      class="table-border table" style="border-collapse: unset; background-color: unset!important">
                                            <template slot="items" slot-scope="props">
                                                <td>{{ props.index + 1}}</td>
                                                <td>{{ props.item.kyHieuSanPham }}</td>
                                                <td>{{ props.item.tenSanPham }}</td>
                                                <td>{{ props.item.soLuong }}</td>
                                                <td>{{ props.item.giaXuat }}</td>
                                                <td class="text-xs-center text-nowrap" style="width:100px;">
                                                    <v-btn icon small class="ma-0" @click="ganGiaTri(props.item)">
                                                        <v-icon small color="teal">edit</v-icon>
                                                    </v-btn>
                                                    <v-btn icon small class="ma-0">
                                                        <v-icon small color="red">delete</v-icon>
                                                    </v-btn>
                                                </td>
                                            </template>
                                            <template slot="no-data">
                                                <p class="text-xs-center mb-0">Không có dữ liệu</p>
                                            </template>
                                        </v-data-table>
                                    </v-flex>
                                </v-layout>

                            </v-flex>
                        </v-layout>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn small class="primary " :disabled="loading" :loading="loading" @click.native="save">{{isUpdate?'Cập nhật':'Thêm mới'}}</v-btn>
                </v-card-actions>
            </v-card>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">
                        Bạn có chắc chắn muốn xóa bản ghi này?
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteChiTietDon" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import { DonDatHang } from '@/models/DonDatHang';
import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
import DonDatHangApi from '@/apiResources/DonDatHangApi';
import { LoaiSanPham } from '@/models/LoaiSanPham';
import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
import { SanPham } from '@/models/SanPham';
import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: { },
        data() {
            return {
                dialog: false,
                isUpdate: false,
                hoaDon: {} as DonDatHang,
                chiTietDon: {} as ChiTietDonDatHang,
                dsChiTietDonDatHang: [] as ChiTietDonDatHang[],
                tableHeader: [
                    { text: '#', value: 'ChiTietDonDatHangID', align: 'center', sortable: true },
                    { text: 'Ký hiệu SP', value: 'DonDatHang.DonDatHangID', align: 'center', sortable: true },
                    { text: 'Tên sản phẩm', value: 'SanPham.SanPhamID', align: 'center', sortable: true },
                    { text: 'Số lượng', value: 'SoLuong', align: 'center', sortable: true },
                    { text: 'Đơn giá xuất', value: 'GiaXuat', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 10 } as ChiTietDonDatHangApiSearchParams,
                loadingTable: false,
                selectedChiTietDonDatHang: {} as ChiTietDonDatHang,
                dialogConfirmDelete: false,
                hoaDonID: 0,
                loading: false,
                dsDanhMuc: [] as LoaiSanPham[],
                dsSanPham: [] as SanPham[],
                searchParamsLoaiSanPham: {} as LoaiSanPhamApiSearchParams,
                searchParamsSanPham: {} as SanPhamApiSearchParams,
                danhMucID:0
            }
        },
        watch: {
        },
        mounted() {
        },
        computed: {
        },
        methods: {
            show(isUpdate: boolean, item: DonDatHang) {
                this.dialog = true;
                this.getDsDanhMuc();
                this.$validator.errors.clear("frmAddEdit");
                this.isUpdate = isUpdate;

                if (this.isUpdate !== true) {
                    this.hoaDon = {} as DonDatHang;
                    this.hoaDon.ngayDat = new Date();
                }
                else {
                    this.hoaDonID = item.donDatHangID;
                    this.searchParamsChiTietDonDatHang.donDatHangID = item.donDatHangID;
                    this.getChiTietDon();
                    this.getDataFromApi(this.hoaDonID);
                }
            },
            getDsDanhMuc() {
                this.searchParamsLoaiSanPham.laDanhMucCha = false;
                LoaiSanPhamApi.search(this.searchParamsLoaiSanPham).then(res => {
                    this.dsDanhMuc = res.data;
                })
            },
            getDsSanPham(id: number) {
                this.searchParamsSanPham.loaiSanPhamID = id;
                SanPhamApi.search(this.searchParamsSanPham).then(res => {
                    this.dsSanPham = res.data;
                })
            },
            getChiTietDon() {
                this.loadingTable = true;
                ChiTietDonDatHangApi.search(this.searchParamsChiTietDonDatHang).then(res => {
                    this.loadingTable = false;
                    this.dsChiTietDonDatHang = res.data;
                });
            },
            getDataFromApi(id: number): void {
                DonDatHangApi.detail(id).then(res => {
                    this.hoaDon = res;

                });
            },
            ganGiaTri(item: any) {
                this.chiTietDon = item;
                this.danhMucID = item.loaiSanPhamID;
                this.getDsSanPham(this.danhMucID);
            },
            hide() {
                this.dialog = false;
            },
            saveChiTietDon(): void {
                this.$validator.validateAll('frmAddEdit1').then((res) => {
                    if (res) {
                        this.chiTietDon.donDatHang = undefined;
                        this.chiTietDon.sanPham = undefined;
                        if (this.chiTietDon.chiTietDonDatHangID != null && this.chiTietDon.chiTietDonDatHangID != 0 && this.chiTietDon.chiTietDonDatHangID != undefined) {
                            this.loading = true;
                            ChiTietDonDatHangApi.update(this.chiTietDon.chiTietDonDatHangID, this.chiTietDon).then(res => {
                                this.loading = false;
                                this.getChiTietDon();
                                this.chiTietDon = { donDatHangID: this.hoaDon.donDatHangID } as ChiTietDonDatHang;
                                this.danhMucID = 0;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            this.chiTietDon.donDatHangID = this.hoaDon.donDatHangID;
                            ChiTietDonDatHangApi.insert(this.chiTietDon).then(res => {
                                this.searchParamsChiTietDonDatHang.donDatHangID = res.donDatHangID;
                                this.getChiTietDon();
                                this.chiTietDon = { donDatHangID: this.hoaDon.donDatHangID } as ChiTietDonDatHang;
                                this.danhMucID = 0;
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
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res : any) => {
                    if (res) {
                        this.hoaDon.lstChiTietDonDatHang = undefined;
                        this.hoaDon.users = undefined;
                        this.hoaDon.users1 = undefined;
                        this.hoaDon.tinhTrang = 5;
                        if (this.isUpdate) {
                            this.loading = true;
                            DonDatHangApi.update(this.hoaDon.donDatHangID, this.hoaDon).then(res => {
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
                            DonDatHangApi.insert(null as any,null as any,null as any,this.hoaDon).then(res => {
                                this.$emit("getData");
                                this.hoaDon = res;
                                this.hoaDonID = res.donDatHangID;
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
            confirmDelete(chiTietDon: ChiTietDonDatHang): void {
                this.selectedChiTietDonDatHang = chiTietDon;
                this.dialogConfirmDelete = true;
            },
            deleteChiTietDon(): void {
                ChiTietDonDatHangApi.delete(this.selectedChiTietDonDatHang.chiTietDonDatHangID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getChiTietDon();
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>
<style>
    .ql-editing {
        left: 0px !important;
        top: -10px !important;
    }

    .theme--light.v-pagination .v-pagination__item {
        display: none !important;
        background: #fff;
        color: #000;
        width: auto;
        min-width: 34px;
        padding: 0 5px;
    }

    .v-responsive {
        cursor: pointer !important;
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

    .v-text-field__suffix {
        padding-left: 4px;
        white-space: nowrap;
        padding-right: 5px !important;
    }
</style>

