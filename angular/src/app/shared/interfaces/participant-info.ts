import { ISearch } from './ISearch';

export interface IParticipantSearch extends ISearch {
    name?: string;
    program?: string;
    lastname?: string;
    client?: string;
    memberId?: string;
    dob?: Date;
    gender?: string;
}

export interface IParticipantResult {
    pages: number;
    items: Array<any>;
}

