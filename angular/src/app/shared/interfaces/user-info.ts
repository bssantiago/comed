export interface IUserInfo {
    basicInfo: IBasicInfo;
    bodyMeasurements: IBodyMeasurements;
    lipidBloodSugar: ILipidBloodSugar;
    tobaccoUse: ITobacooUse;
}

export interface IBasicInfo {
    name: string;
    lastname: string;
    client: string;
    memberId: string;
    drawType: string;
    program: string;
    essmentDate: Date;
    address?: string;
}

export interface IBodyMeasurements {
    sistolic: number;
    diastolic: number;
    height: IHeight;
    weight: number;
    waist: number;
    bodyFat: number;
}

export interface IHeight {
    ft: number;
    in: number;
}

export interface ILipidBloodSugar {
    fasting: boolean;
    cholesterol: number;
    hdl: number;
    triglycerides: number;
    ldl: number;
    glucose: number;
    hba1c: number;
}

export interface ITobacooUse {
    use: boolean;
}

export interface IUserSearch {
    name?: string;
    program?: string;
    lastname?: string;
    client?: string;
    memberId?: string;
    dob?: Date;
    gender?: string;
    page?: number;
    pageSize?: number;
}

export interface IKeyValues {
    key: string;
    value: string;
}
