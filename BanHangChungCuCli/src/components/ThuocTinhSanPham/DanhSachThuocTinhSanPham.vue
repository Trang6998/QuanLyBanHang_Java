<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/thuoctinhsanpham" exact>ThuocTinhSanPham</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                :items="dsThuocTinhSanPham"
                                @update:pagination="getDataFromApi" :pagination.sync="searchParamsThuocTinhSanPham"
                                :total-items="searchParamsThuocTinhSanPham.totalItems"
                                :loading="loadingTable"
                                class="table-border table">
                        <template slot="items" slot-scope="props">
                    <td>{{ props.item.ThuocTinhSanPhamID }}</td>
                            <td>{{ props.item.SanPham.SanPhamID }}</td>
                            <td>{{ props.item.ThuocTinh.ThuocTinhID }}</td>
                    <td>{{ props.item.NoiDungMoTaChiTiet }}</td>
                    <td class="text-xs-center" style="width:80px;">
                        <v-btn flat icon small :to="'/thuoctinhsanpham/'+props.item.ThuocTinhSanPhamID" class="ma-0">
                            <v-icon small>edit</v-icon>
                        </v-btn>
                        <v-btn flat color="red" icon small class="ma-0" @click="confirmDelete(props.item)">
                            <v-icon small>delete</v-icon>
                        </v-btn>
                    </td>
                            </template>
                        </v-data-table>
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
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ThuocTinhSanPhamApi, { ThuocTinhSanPhamApiSearchParams } from '@/apiResources/ThuocTinhSanPhamApi';
    import { ThuocTinhSanPham } from '@/models/ThuocTinhSanPham';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsThuocTinhSanPham: [] as ThuocTinhSanPham[],
                tableHeader: [
                    { text: 'ThuocTinhSanPhamID', value: 'ThuocTinhSanPhamID', align: 'center', sortable: true },
                    { text: 'SanPhamID', value: 'SanPham.SanPhamID', align: 'center', sortable: true },
                    { text: 'ThuocTinhID', value: 'ThuocTinh.ThuocTinhID', align: 'center', sortable: true },
                    { text: 'NoiDungMoTa', value: 'NoiDungMoTa', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsThuocTinhSanPham: { includeEntities: true, rowsPerPage: 10 } as ThuocTinhSanPhamApiSearchParams,
                loadingTable: false,
                selectedThuocTinhSanPham: {} as ThuocTinhSanPham,
                dialogConfirmDelete: true,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsThuocTinhSanPham);
        },
        methods: {
            getDataFromApi(searchParamsThuocTinhSanPham: ThuocTinhSanPhamApiSearchParams): void {
                this.loadingTable = true;
                ThuocTinhSanPhamApi.search(searchParamsThuocTinhSanPham).then(res => {
                    this.dsThuocTinhSanPham = res.data;
                    this.searchParamsThuocTinhSanPham.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
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

