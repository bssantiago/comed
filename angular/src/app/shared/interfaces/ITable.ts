import { IKeyValues } from './user-info';

export interface IDynamicTable {
    currentPage: number;
    data: Array<any>;
    header: Array<IKeyValues>;
    pages: number;
    pageSize: number;
    filter: object;
}

export interface IListTableItems<T> {
    items: Array<T>;
    pages: number;
}
