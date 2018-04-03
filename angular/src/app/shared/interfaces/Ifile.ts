export interface IFile {
    clientId: string;
    programId: string;
    range: IRange;
    rewardDate: Date;
    data: File;
}

export interface IRange {
    start: Date;
    end: Date;
}
