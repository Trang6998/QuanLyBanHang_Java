<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12>
                <v-layout row wrap>
                    <v-flex xs12>
                        <h3>Danh sách sản phẩm</h3>
                    </v-flex>
                    <v-flex xs12>
                        <v-layout row wrap>
                            <v-flex xs12 md4>
                                <v-text-field label="Tìm kiếm" v-model="searchParamsSanPham.tenSanPham" @input="getDataFromApi(searchParamsSanPham)" ma-0 pa-0></v-text-field>
                            </v-flex>
                            <v-flex xs6 md4>
                                <v-autocomplete v-model="searchParamsSanPham.loaiSanPhamID"
                                                placeholder="Chọn danh mục"
                                                label="Danh mục"
                                                :items="dsDanhMuc"
                                                item-text="tenLoai"
                                                item-value="loaiSanPhamID"
                                                @input="getDataFromApi(searchParamsSanPham)">
                                </v-autocomplete>
                            </v-flex>
                            <v-flex xs6 md4>
                                <v-autocomplete :items="dsToaNha"
                                                placeholder="Chọn chung cư"
                                                label="Chọn chung cư"
                                                v-model="searchParamsSanPham.toaNhaID"
                                                @change="getDataFromApi(searchParamsSanPham)"
                                                item-value="ToaNhaId"
                                                item-text="TenToaNha"></v-autocomplete>
                            </v-flex>
                            <v-flex xs12>
                                <v-layout norwap>
                                    <v-spacer></v-spacer>
                                    <v-btn small @click="showModalThemSuaSanPham(false,{anhSanPham: null})" style="margin-top: auto" color="primary">Thêm sản phẩm</v-btn>
                                </v-layout>
                            </v-flex>
                        </v-layout>
                    </v-flex>
                    <v-flex xs12>
                        <v-data-table :headers="tableHeader"
                                      :items="dsSanPham"
                                      hide-actions :pagination.sync="searchParamsSanPham"
                                      :loading="loadingTable"
                                      class="table-border table">
                            <template slot="items" slot-scope="props">
                                <td class="text-xs-center">{{ props.index + 1 }}</td>
                                <td>{{ props.item.kyHieuSanPham }}</td>
                                <td>{{ props.item.tenSanPham }}</td>
                                <td>{{ props.item.tenLoai }}</td>
                                <!--<td>{{ props.item.soLuong }}</td>-->
                                <td>{{ props.item.giaBan | currency('', 0,{ thousandsSeparator: '.' })}}</td>
                                <td>{{ props.item.tenDonVi }}</td>
                                <td>{{ props.item.nhaCungCap }}</td>
                                <td class="text-xs-center text-nowrap" style="width:100px;white-space: nowrap">
                                    <v-btn icon small class="ma-0" @click="showModalThemSuaSanPham(true,props.item)">
                                        <v-icon small color="teal">edit</v-icon>
                                    </v-btn>
                                    <v-btn icon small class="ma-0" @click="confirmDelete(props.item)">
                                        <v-icon small color="red">delete</v-icon>
                                    </v-btn>
                                </td>
                            </template>
                            <template slot="no-data">
                                <p class="text-xs-center mb-0">Không có dữ liệu</p>
                            </template>
                        </v-data-table>
                        <div class="text-xs-center pt-2 xxx">
                            <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsSanPham)" v-model="page" :length="searchParamsSanPham.totalPages"></v-pagination>
                        </div>
                    </v-flex>
                </v-layout>
            </v-flex>
            <them-sua-san-pham ref="themSuaSanPham" @getData="getDataFromApi(searchParamsSanPham)"></them-sua-san-pham>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">
                        Bạn có chắc chắn muốn xóa sản phẩm này?
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteSanPham" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
        
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ThemSuaSanPham from './ThemSuaSanPham.vue'
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { LoaiSanPham } from '@/models/LoaiSanPham';
    import { HTTP } from '@/http-servicesNew';
import SanPhamApi, { SanPhamApiSearchParams } from '@/apiResources/SanPhamApi';
import { SanPham } from '@/models/SanPham';
    export default Vue.extend({
        components: { ThemSuaSanPham },
        data() {
            return {
                dsDanhMuc: [] as LoaiSanPham[],
                dsSanPham: [] as SanPham[],
                dsToaNha:[] as any,
                tableHeader: [
                    { text: '#', value: '#', align: 'center', sortable: false },
                    { text: 'Ký hiệu', value: 'kyHieuSanPham', align: 'center', sortable: false },
                    { text: 'Tên sản phẩm', value: 'tenSanPham', align: 'center', sortable: false },
                    { text: 'Danh mục', value: 'tenLoai', align: 'center', sortable: false },
                    //{ text: 'Số lượng', value: 'soLuong', align: 'center', sortable: false },
                    { text: 'Đơn giá bán', value: 'giaBan', align: 'center', sortable: false },
                    { text: 'Đơn vị', value: 'tenDonVi', align: 'center', sortable: false },
                    { text: 'Nhà cung cấp', value: '#', align: 'center', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsSanPham: { includeEntities: true, rowsPerPage: 10 } as SanPhamApiSearchParams,
                searchParamsLoaiSanPham: {} as LoaiSanPhamApiSearchParams,
                loadingTable: false,
                selectedSanPham: {} as SanPham,
                dialogConfirmDelete: false,
                page: 1
            }
        },
        watch: {
            page: function () {
                this.searchParamsSanPham.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsSanPham.rowsPerPage == null ||
                    this.searchParamsSanPham.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsSanPham.totalItems / this.searchParamsSanPham.rowsPerPage);
            }
        },
        created() {
            this.getDanhSachToaNha();
            this.getDanhMuc();
            this.getDataFromApi(this.searchParamsSanPham);
        },
        methods: {
            getDataFromApi(searchParamsSanPham: LoaiSanPhamApiSearchParams): void {
                this.loadingTable = true;
                SanPhamApi.search(searchParamsSanPham).then(res => {
                    this.dsSanPham = res.data;
                    this.searchParamsSanPham.totalItems = res.pagination.totalItems;
                    this.searchParamsSanPham.page = (res.pagination.page as any) + 1;
                    this.searchParamsSanPham.totalPages = res.pagination.totalPages;
                    this.loadingTable = false;
                });
            },
            showModalThemSuaSanPham(isUpdate: boolean, item: SanPham) {
                (this.$refs.themSuaSanPham as any).show(isUpdate, item);
            },
            getDanhMuc() {
                this.searchParamsLoaiSanPham.laDanhMucCha = false;
                LoaiSanPhamApi.search(this.searchParamsLoaiSanPham).then(res => {
                    this.dsDanhMuc.push({
                        loaiSanPhamID: null as any,
                        tenLoai: 'Tất cả'
                    } as LoaiSanPham);
                    this.dsDanhMuc.push(...res.data);
                    this.searchParamsSanPham.loaiSanPhamID = null as any;
                });
            },
            confirmDelete(sanPham: SanPham): void {
                this.selectedSanPham = sanPham;
                this.dialogConfirmDelete = true;
            },
            deleteSanPham(): void {
                SanPhamApi.delete(this.selectedSanPham.sanPhamID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsSanPham);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error(res.response.data.message);
                });
            },
            getDanhSachToaNha() {
                HTTP.get('odata/ToaNha').then(res => {
                    this.dsToaNha.push({
                        ToaNhaId: null as any,
                        TenToaNha: 'Tất cả'
                    } as any);
                    this.dsToaNha.push(...res.data.value);
                    this.searchParamsSanPham.toaNhaID = this.$store.state.user.User.ToaNhaId;
                })
            },
        }
    });
</script>
<style>
    .v-input--selection-controls {
        margin-top: 0px;
        padding-top: 0px;
    }

    .v-input {
        -webkit-box-align: start;
        -ms-flex-align: start;
        align-items: flex-start;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-flex: 1;
        -ms-flex: 1 1 auto;
        flex: 1 1 auto;
        padding-top: 0px;
        text-align: left;
    }

        .v-input .v-label {
            height: 20px;
            line-height: 20px;
            font-size: 14px;
        }
</style>
