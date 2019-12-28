<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12>
                <v-layout row wrap>
                    <v-flex xs12>
                        <h3>Danh sách danh mục</h3>
                    </v-flex>
                    <v-flex xs12>
                        <v-layout row wrap>
                            <v-flex xs12 md4>
                                <v-text-field ma-0 pa-0 label="Tìm kiếm" hide-details v-model="searchParamsDanhMuc.tenLoaiSanPham" @input="getDataFromApi(searchParamsDanhMuc)" ma-0 pa-0></v-text-field>
                            </v-flex>
                            <v-flex xs12 md4>
                                <v-radio-group hide-details v-model="searchParamsDanhMuc.laDanhMucCha" @change="getDataFromApi(searchParamsDanhMuc)" row>
                                    <v-radio label="Danh mục cha"
                                             value="true"></v-radio>
                                    <v-radio label="Danh mục con"
                                             value="false"></v-radio>
                                </v-radio-group>
                            </v-flex>
                            <v-flex xs12>
                                <v-layout norwap>
                                    <v-spacer></v-spacer>
                                    <v-btn small @click="showModalThemSuaDanhMuc(false,{anhDaiDien: null})" style="margin-top: auto" color="primary">Thêm danh mục</v-btn>
                                </v-layout>
                            </v-flex>
                        </v-layout>
                    </v-flex>
                    <v-flex xs12>
                        <v-data-table :headers="tableHeader"
                                      :items="dsDanhMuc"
                                      hide-actions :pagination.sync="searchParamsDanhMuc"
                                      :loading="loadingTable"
                                      class="table-border table">
                            <template slot="items" slot-scope="props">
                                <td class="text-xs-center">{{ props.index + 1 }}</td>
                                <td>{{ props.item.tenLoai }}</td>
                                <td>{{ props.item.moTa }}</td>
                                <td v-if="props.item.danhMucCon.length == 0"><a>{{props.item.soSanPham}} sản phẩm</a></td>
                                <td v-else>
                                    <b>{{props.item.soSanPham}} sản phẩm, trong đó: <br /></b>
                                    <a v-for="(dm, i) in props.item.danhMucCon" :key="i">
                                        +{{dm.tenLoai}}: {{dm.soSanPham}} sản phẩm <br />
                                    </a>
                                </td>
                                <td class="text-xs-center text-nowrap" style="width:100px; white-space: nowrap">
                                    <v-btn icon small class="ma-0" @click="showModalThemSuaDanhMuc(true,props.item)">
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
                            <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsDanhMuc)" v-model="page" :length="searchParamsDanhMuc.totalPages"></v-pagination>
                        </div>
                    </v-flex>
                </v-layout>
            </v-flex>
            <them-sua-danh-muc ref="themSuaDanhMuc" @getData="getDataFromApi(searchParamsDanhMuc)"></them-sua-danh-muc>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">
                        Bạn có chắc chắn muốn xóa danh mục này?
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteDichVu" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ThemSuaDanhMuc from './ThemSuaDanhMuc.vue'
import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
import { LoaiSanPham } from '@/models/LoaiSanPham';
    export default Vue.extend({
        components: { ThemSuaDanhMuc },
        data() {
            return {
                dsDanhMuc: [] as LoaiSanPham[],
                tableHeader: [
                    { text: '#', value: '#', align: 'center', sortable: false },
                    { text: 'Tên danh mục', value: 'tenLoaiSanPham', align: 'center', sortable: false },
                    { text: 'Mô tả', value: 'moTa', align: 'center', sortable: false },
                    { text: 'Xem sản phẩm', value: '#', align: 'center', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDanhMuc: { includeEntities: true, rowsPerPage: 10 } as LoaiSanPhamApiSearchParams,
                loadingTable: false,
                selectedDanhMuc: {} as LoaiSanPham,
                dialogConfirmDelete: false,
                page: 1
            }
        },
        watch: {
            page: function () {
                this.searchParamsDanhMuc.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsDanhMuc.rowsPerPage == null ||
                    this.searchParamsDanhMuc.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsDanhMuc.totalItems / this.searchParamsDanhMuc.rowsPerPage);
            }
        },
        created() {
            this.getDataFromApi(this.searchParamsDanhMuc);
            this.searchParamsDanhMuc.laDanhMucCha = "true" as any;
        },
        methods: {
            getDataFromApi(searchParamsDanhMuc: LoaiSanPhamApiSearchParams): void {
                this.loadingTable = true;
                LoaiSanPhamApi.search(searchParamsDanhMuc).then(res => {
                    this.dsDanhMuc = res.data;
                    this.searchParamsDanhMuc.totalItems = res.pagination.totalItems;
                    this.searchParamsDanhMuc.page = (res.pagination.page as any) + 1;
                    this.searchParamsDanhMuc.totalPages = res.pagination.totalPages;
                    this.loadingTable = false;
                });
            },
            showModalThemSuaDanhMuc(isUpdate: boolean, item: LoaiSanPham) {
                (this.$refs.themSuaDanhMuc as any).show(isUpdate, item, this.searchParamsDanhMuc.laDanhMucCha);
            },
            confirmDelete(DanhMuc: LoaiSanPham): void {
                this.selectedDanhMuc = DanhMuc;
                this.dialogConfirmDelete = true;
            },
            deleteDichVu(): void {
                LoaiSanPhamApi.delete(this.selectedDanhMuc.loaiSanPhamID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDanhMuc);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error(res.response.data.message);
                });
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
