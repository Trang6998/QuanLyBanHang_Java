<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/loaisanpham" exact>LoaiSanPham</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                :items="dsLoaiSanPham"
                                @update:pagination="getDataFromApi" :pagination.sync="searchParamsLoaiSanPham"
                                :total-items="searchParamsLoaiSanPham.totalItems"
                                :loading="loadingTable"
                                class="table-border table">
                        <template slot="items" slot-scope="props">
                    <td>{{ props.item.LoaiSanPhamID }}</td>
                            <td>{{ props.item.LoaiSanPham.LoaiSanPhamID }}</td>
                    <td>{{ props.item.TenLoai }}</td>
                    <td>{{ props.item.MoTa }}</td>
                    <td class="text-xs-center" style="width:80px;">
                        <v-btn flat icon small :to="'/loaisanpham/'+props.item.LoaiSanPhamID" class="ma-0">
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
                    <v-btn color="red darken-1" @click.native="deleteLoaiSanPham" flat>Xác Nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import LoaiSanPhamApi, { LoaiSanPhamApiSearchParams } from '@/apiResources/LoaiSanPhamApi';
    import { LoaiSanPham } from '@/models/LoaiSanPham';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsLoaiSanPham: [] as LoaiSanPham[],
                tableHeader: [
                    { text: 'LoaiSanPhamID', value: 'LoaiSanPhamID', align: 'center', sortable: true },
                    { text: 'LoaiSanPhamPID', value: 'LoaiSanPham.LoaiSanPhamID', align: 'center', sortable: true },
                    { text: 'TenLoai', value: 'TenLoai', align: 'center', sortable: true },
                    { text: 'MoTa', value: 'MoTa', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsLoaiSanPham: { includeEntities: true, rowsPerPage: 10 } as LoaiSanPhamApiSearchParams,
                loadingTable: false,
                selectedLoaiSanPham: {} as LoaiSanPham,
                dialogConfirmDelete: false,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsLoaiSanPham);
        },
        methods: {
            getDataFromApi(searchParamsLoaiSanPham: LoaiSanPhamApiSearchParams): void {
                this.loadingTable = true;
                LoaiSanPhamApi.search(searchParamsLoaiSanPham).then(res => {
                    this.dsLoaiSanPham = res.data;
                    this.searchParamsLoaiSanPham.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            confirmDelete(loaiSanPham: LoaiSanPham): void {
                this.selectedLoaiSanPham = loaiSanPham;
                this.dialogConfirmDelete = true;
            },
            deleteLoaiSanPham(): void {
                LoaiSanPhamApi.delete(this.selectedLoaiSanPham.loaiSanPhamID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsLoaiSanPham);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>

