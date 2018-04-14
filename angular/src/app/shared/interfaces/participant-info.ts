
export interface IParticipantSearch {
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

export interface IParticipantResult {
    pages: number;
    participants: Array<any>;
}

