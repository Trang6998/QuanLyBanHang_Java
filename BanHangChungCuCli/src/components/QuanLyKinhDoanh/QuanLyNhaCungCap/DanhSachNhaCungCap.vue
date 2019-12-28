<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12>
                <h3>Danh sách nhà cung cấp</h3>
            </v-flex>
            <v-flex xs12>
                <v-layout row wrap>
                    <v-flex xs12 md4>
                        <v-text-field ma-0 pa-0 label="Tìm kiếm" hide-details v-model="searchParamsNhaCungCap.tenNhaCungCap" @input="getDataFromApi(searchParamsNhaCungCap)"></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                        <v-layout norwap>
                            <v-spacer></v-spacer>
                            <v-btn small @click="showModalThemSuaNhaCungCap(false,{})" style="margin-top: auto" color="primary">Thêm nhà cung cấp</v-btn>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
            <v-layout row wrap>
                <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                  :items="dsNhaCungCap"
                                  hide-actions :pagination.sync="searchParamsNhaCungCap"
                                  :loading="loadingTable"
                                  class="table-border table">
                        <template slot="items" slot-scope="props">
                            <td class="text-xs-center">{{ props.item.maNhaCungCap }}</td>
                            <td class="text-xs-center">{{ props.item.tenNhaCungCap }}</td>
                            <td class="text-xs-center">{{ props.item.diaChi }}</td>
                            <td class="text-xs-center">{{ props.item.soDienThoai }}</td>
                            <td class="text-xs-center">{{ props.item.email }}</td>
                            <td class="text-xs-center">{{ props.item.ghiChu }}</td>
                            <td class="text-xs-center" style="width:80px;">
                                <v-btn flat icon small @click="showModalThemSuaNhaCungCap(true,props.item)" class="ma-0">
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
                        <v-pagination :total-visible="5" @input="getDataFromApi(searchParamsNhaCungCap)" v-model="page" :length="searchParamsNhaCungCap.totalPages"></v-pagination>
                    </div>
                </v-flex>
            </v-layout>
            <them-sua-nha-cung-cap ref="themSuaNhaCungCap" @getData="getDataFromApi(searchParamsNhaCungCap)"></them-sua-nha-cung-cap>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteNhaCungCap" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
        
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import NhaCungCapApi, { NhaCungCapApiSearchParams } from '@/apiResources/NhaCungCapApi';
    import { NhaCungCap } from '@/models/NhaCungCap';
    import ThemSuaNhaCungCap from './ThemSuaNhaCungCap.vue'
    export default Vue.extend({
        components: { ThemSuaNhaCungCap},
        data() {
            return {
                dsNhaCungCap: [] as NhaCungCap[],
                tableHeader: [
                    { text: 'Mã NCC', value: 'maNhaCungCap', align: 'center', sortable: false },
                    { text: 'Tên nhà cung cấp', value: 'tenNhaCungCap', align: 'center', sortable: false },
                    { text: 'Địa chỉ', value: 'diaChi', align: 'center', sortable: false },
                    { text: 'Số điện thoại', value: 'soDienThoai', align: 'center', sortable: false },
                    { text: 'Email', value: 'email', align: 'center', sortable: false },
                    { text: 'Ghi chú', value: 'ghiChu', align: 'center', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsNhaCungCap: { includeEntities: true, rowsPerPage: 10 } as NhaCungCapApiSearchParams,
                loadingTable: false,
                selectedNhaCungCap: {} as NhaCungCap,
                dialogConfirmDelete: false,
                page: 1
            }
        },
        watch: {
            page: function () {
                this.searchParamsNhaCungCap.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsNhaCungCap.rowsPerPage == null ||
                    this.searchParamsNhaCungCap.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsNhaCungCap.totalItems / this.searchParamsNhaCungCap.rowsPerPage);
            }
        },
        created() {
            this.getDataFromApi(this.searchParamsNhaCungCap);
        },
        methods: {
            getDataFromApi(searchParamsNhaCungCap: NhaCungCapApiSearchParams): void {
                this.loadingTable = true;
                NhaCungCapApi.search(searchParamsNhaCungCap).then(res => {
                    this.dsNhaCungCap = res.data;
                    this.searchParamsNhaCungCap.totalItems = res.pagination.totalItems;
                    this.searchParamsNhaCungCap.page = (res.pagination.page as any) + 1;
                    this.searchParamsNhaCungCap.totalPages = res.pagination.totalPages;
                    this.loadingTable = false;
                });
            },
            showModalThemSuaNhaCungCap(isUpdate: boolean, item: NhaCungCap) {
                (this.$refs.themSuaNhaCungCap as any).show(isUpdate, item);
            },
            confirmDelete(nhaCungCap: NhaCungCap): void {
                this.selectedNhaCungCap = nhaCungCap;
                this.dialogConfirmDelete = true;
            },
            deleteNhaCungCap(): void {
                NhaCungCapApi.delete(this.selectedNhaCungCap.nhaCungCapID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsNhaCungCap);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>

