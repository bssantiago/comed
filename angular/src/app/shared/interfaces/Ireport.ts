export interface IBioGuidline {
    sistolic: Array<IRangeGuid>;
    diastolic: Array<IRangeGuid>;
    cholesterol: Array<IRangeGuid>;
    hdl: Array<IRangeGuid>;
    ldl: Array<IRangeGuid>;
    triglycerides: Array<IRangeGuid>;
    fasting: Array<IRangeGuid>;
    hba1c: Array<IRangeGuid>;
    bmi: Array<IRangeGuid>;
    waist: Array<IRangeGuid>;
    body_fat: Array<IRangeGuid>;
}

export interface IGuidline {
    ranges: Array<IRangeGuid>;
    name: string;
}

export interface Dictionary<T> {
    [Key: string]: T;
}

export interface IRangeGuid {
    min: number;
    max: number;
    color: string;
    name: string;
}
