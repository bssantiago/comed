export interface IGenericResponse<T> {
    meta: IMetaInfo;
    response: T;
}

export interface IMetaInfo {
    errCode: number;
    msg: string;
}
