<template>
    <v-dialog v-model="dialog" width="600" persistent>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>Thuộc tính sản phẩm</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <v-layout row wrap>
                        <v-flex xs12>

                            <v-form scope="frmAddEdit">
                                <v-layout row wrap>
                                    <v-flex xs10>
                                        <v-layout row wrap>
                                            <v-flex xs12 sm6>
                                                <v-autocomplete v-model="thuocTinhSanPham.thuocTinhID"
                                                                label="Thuộc tính"
                                                                :items="dsThuocTinh"
                                                                item-text="tenThuocTinh"
                                                                item-value="thuocTinhID"
                                                                :error-messages="errors.collect('Thuộc tính', 'frmAddEdit')"
                                                                v-validate="'required'"
                                                                data-vv-scope="frmAddEdit"
                                                                data-vv-name="Thuộc tính"
                                                                clearable>
                                                </v-autocomplete>
                                            </v-flex>

                                            <v-flex xs12>
                                                <v-textarea rows="3"
                                                            v-model="thuocTinhSanPham.noiDungMoTa"
                                                            label="Nội dung mô tả"
                                                            :error-messages="errors.collect('Nội dung mô tả', 'frmAddEdit')"
                                                            v-validate="'required'"
                                                            data-vv-scope="frmAddEdit"
                                                            data-vv-name="Nội dung mô tả">
                                                </v-textarea>
                                            </v-flex>
                                        </v-layout>
                                    </v-flex>
                                    <v-flex xs2>
                                        <v-btn flat color="green" @click="save">
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
                                          :items="dsThuocTinhSanPham"
                                          hide-actions :pagination.sync="searchParamsThuocTinhSanPham"
                                          :loading="loadingTable"
                                          class="table-border table">
                                <template slot="items" slot-scope="props">
                                    <td>{{ props.index+1 }}</td>
                                    <td>{{ props.item.thuocTinh ? props.item.thuocTinh.tenThuocTinh :"" }}</td>
                                    <td>{{ props.item.noiDungMoTa }}</td>
                                    <td class="text-xs-center" style="width:80px;">
                                        <v-btn flat icon small class="ma-0" @click="ganBien(props.item)">
                                            <v-icon small>edit</v-icon>
                                        </v-btn>
                                        <v-btn flat color="red" icon small class="ma-0" @click="confirmDelete(props.item)">
                                            <v-icon small>delete</v-icon>
                                        </v-btn>
                                    </td>
                                </template>
                                <template slot="no-data">
                                    <p class="text-xs-center mb-0">Không có dữ liệu</p>
                                </template>
                            </v-data-table>
                            <div class="text-xs-center pt-2 xxx">
                                <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsThuocTinhSanPham)" v-model="page" :length="searchParamsThuocTinhSanPham.totalPages"></v-pagination>
                            </div>
                        </v-flex>
                    </v-layout>
                </v-card-text>
            </v-card>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteThuocTinhSanPham" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { LoaiSanPham } from '@/models/LoaiSanPham';
    import { HTTP } from '@/HTTPServices';
    import { ThuocTinhSanPham } from '@/models/ThuocTinhSanPham';
    import ThuocTinhSanPhamApi, { ThuocTinhSanPhamApiSearchParams } from '@/apiResources/ThuocTinhSanPhamApi';
    import { ThuocTinh } from '@/models/ThuocTinh';
    import ThuocTinhApi, { ThuocTinhApiSearchParams } from '@/apiResources/ThuocTinhApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                dialog: false,
                isUpdate: false,
                dsThuocTinhSanPham: [] as ThuocTinhSanPham[],
                tableHeader: [
                    { text: '#', value: 'ThuocTinhsanPhamID', align: 'center', sortable: true },
                    { text: 'Loại thuộc tính', value: 'SanPham.sanPhamID', align: 'center', sortable: true },
                    { text: 'Nội dung mô tả', value: 'NoiDungMoTa', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsThuocTinhSanPham: { includeEntities: true, rowsPerPage: 5 } as ThuocTinhSanPhamApiSearchParams,
                loadingTable: false,
                selectedThuocTinhSanPham: {} as ThuocTinhSanPham,
                dialogConfirmDelete: false,
                thuocTinhSanPham: {} as ThuocTinhSanPham,
                sanPhamID: 0,
                thuocTinhsanPhamID: 0,
                loading: false,
                dsThuocTinh: [] as ThuocTinh[],
                searchParamsThuocTinh: { includeEntities: true, rowsPerPage: 0 } as ThuocTinhApiSearchParams,
                page: 1
            }
        },
        watch: {
            page: function () {
                this.searchParamsThuocTinhSanPham.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsThuocTinhSanPham.rowsPerPage == null ||
                    this.searchParamsThuocTinhSanPham.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsThuocTinhSanPham.totalItems / this.searchParamsThuocTinhSanPham.rowsPerPage);
            }
        },
        mounted() {
        },
        methods: {
            show(idSanPham: number) {
                this.dialog = true;
                this.getThuocTinh();
                this.$validator.errors.clear("frmAddEdit");
                this.sanPhamID = idSanPham;
                this.thuocTinhSanPham = { sanPhamID: this.sanPhamID } as ThuocTinhSanPham;
                this.getDataFromApi(this.searchParamsThuocTinhSanPham);
            },
            getDataFromApi(searchParamsThuocTinhSanPham: ThuocTinhSanPhamApiSearchParams): void {
                searchParamsThuocTinhSanPham.sanPhamID = this.sanPhamID;
                ThuocTinhSanPhamApi.search(this.searchParamsThuocTinhSanPham).then(res => {
                    // this.searchParamsThuocTinhSanPham.totalItems = res.pagination.totalItems;
                    // this.searchParamsThuocTinhSanPham.page = (res.pagination.page as any) + 1;
                    // this.searchParamsThuocTinhSanPham.totalPages = res.pagination.totalPages;
                    this.dsThuocTinhSanPham = res as any;
                });
            },
            getThuocTinh() {
                ThuocTinhApi.search(this.searchParamsThuocTinh).then(res => {
                    this.dsThuocTinh = res as any;
                });
            },
            hide() {
                this.dialog = false;
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                    if (res) {
                        this.thuocTinhSanPham.sanPham = undefined;
                        this.thuocTinhSanPham.thuocTinh = undefined;
                        if (this.thuocTinhSanPham.thuocTinhSanPhamID != null && this.thuocTinhSanPham.thuocTinhSanPhamID != 0 && this.thuocTinhSanPham.thuocTinhSanPhamID != undefined) {
                            this.loading = true;
                            ThuocTinhSanPhamApi.update(this.thuocTinhSanPham.thuocTinhSanPhamID, this.thuocTinhSanPham).then(res => {
                                this.loading = false;
                                this.getDataFromApi(this.searchParamsThuocTinhSanPham);
                                this.thuocTinhSanPham = { sanPhamID: this.sanPhamID } as ThuocTinhSanPham;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            ThuocTinhSanPhamApi.insert(this.thuocTinhSanPham).then(res => {
                                this.getDataFromApi(this.searchParamsThuocTinhSanPham);
                                this.thuocTinhSanPham = { sanPhamID: this.sanPhamID } as ThuocTinhSanPham;
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
            ganBien(thuocTinhSanPham: ThuocTinhSanPham) {
                this.thuocTinhSanPham = thuocTinhSanPham;
            },
            confirmDelete(thuocTinhSanPham: ThuocTinhSanPham): void {
                this.selectedThuocTinhSanPham = thuocTinhSanPham;
                this.dialogConfirmDelete = true;
            },
            deleteThuocTinhSanPham(): void {
                ThuocTinhSanPhamApi.delete(this.selectedThuocTinhSanPham.thuocTinhSanPhamID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsThuocTinhSanPham);
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
</style>

