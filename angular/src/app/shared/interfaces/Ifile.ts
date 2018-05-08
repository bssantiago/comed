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

export interface IFileModal {
    clientId: string;
    programId: string;
    ptrogramDisplayName: string;
    merked: boolean;
}

export interface IFileTable {
    client_name: string;
    program_display_name: string;
    file_name: string;
}
