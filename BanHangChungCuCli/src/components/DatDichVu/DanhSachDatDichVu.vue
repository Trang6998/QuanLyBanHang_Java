<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/datdichvu" exact>DatDichVu</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-layout row wrap>
                    <v-flex xs12>
                    <v-data-table :headers="tableHeader"
                                :items="dsDatDichVu"
                                @update:pagination="getDataFromApi" :pagination.sync="searchParamsDatDichVu"
                                :total-items="searchParamsDatDichVu.totalItems"
                                :loading="loadingTable"
                                class="table-border table">
                        <template slot="items" slot-scope="props">
                    <td>{{ props.item.DatDichVuID }}</td>
                            <td>{{ props.item.Users.UserId }}</td>
                            <td>{{ props.item.DichVu.DichVuID }}</td>
                            <td>{{ props.item.NgayDat === null ? "" : props.item.NgayDat|moment('DD/MM/YYYY HH:mm:ss') }}</td>
                    <td>{{ props.item.GhiChu }}</td>
                    <td class="text-xs-center" style="width:80px;">
                        <v-btn flat icon small :to="'/datdichvu/'+props.item.DatDichVuID" class="ma-0">
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
                    <v-btn color="red darken-1" @click.native="deleteDatDichVu" flat>Xác Nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';
    import { DatDichVu } from '@/models/DatDichVu';

    export default Vue.extend({
        components: {},
        data() {
            return {
                dsDatDichVu: [] as DatDichVu[],
                tableHeader: [
                    { text: 'DatDichVuID', value: 'DatDichVuID', align: 'center', sortable: true },
                    { text: 'UserID', value: 'Users.UserId', align: 'center', sortable: true },
                    { text: 'DichVuID', value: 'DichVu.DichVuID', align: 'center', sortable: true },
                    { text: 'NgayDat', value: 'NgayDat', align: 'center', sortable: true },
                    { text: 'GhiChu', value: 'GhiChu', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDatDichVu: { includeEntities: true, rowsPerPage: 10 } as DatDichVuApiSearchParams,
                loadingTable: false,
                selectedDatDichVu: {} as DatDichVu,
                dialogConfirmDelete: false,
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsDatDichVu);
        },
        methods: {
            getDataFromApi(searchParamsDatDichVu: DatDichVuApiSearchParams): void {
                this.loadingTable = true;
                DatDichVuApi.search(searchParamsDatDichVu).then(res => {
                    this.dsDatDichVu = res.data;
                    this.searchParamsDatDichVu.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            confirmDelete(datDichVu: DatDichVu): void {
                this.selectedDatDichVu = datDichVu;
                this.dialogConfirmDelete = true;
            },
            deleteDatDichVu(): void {
                DatDichVuApi.delete(this.selectedDatDichVu.datDichVuID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDatDichVu);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>

