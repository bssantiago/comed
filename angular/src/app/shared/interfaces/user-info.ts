export interface IUserInfo {
    biometric_id?: number;
    first_name?: string;
    last_name?: string;
    member_id?: number;
    client_id?: number;
    draw_type?: string;
    date_of_birth?: Date;
    program_id?: number;
    program_display_name?: string;
    participant_id?: number;
    sistolic: number;
    diastolic: number;
    height: IHeight;
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
    assessment_date?: Date;
    reward_date?: Date;
    create_date?: Date;
}

export interface IKeyValues {
    key: string;
    value: string;
    date?: boolean;
}

export interface IHeight {
    feet: number;
    inches: number;
}
