export interface IGenericResponse {
    meta: IMetaInfo;
    response: any;
}

export interface IMetaInfo {
    errCode: number;
    msg: string;
}
