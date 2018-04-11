export interface IUserInfo {
    first_name?: string;
    last_name?: string;
    member_id?: number;
    client_id?: number;
    draw_type?: number;
    date_of_birth?: string;
    program_id?: number;
    participant_id?: number;
    sistolic: number;
    diastolic: number;
    height: number;
    weight: number;
    waist: number;
    body_fat: number;
    cholesterol: number;
    hdl: number;
    triglycerides: number;
    ldl: number;
    glucose: number;
    hba1c: number;
    fasting: boolean;
    tobacco_use: boolean;
    duration: number;
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
