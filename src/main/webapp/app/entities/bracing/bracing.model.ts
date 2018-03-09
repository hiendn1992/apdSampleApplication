import { BaseEntity } from './../../shared';

export class Bracing implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
    ) {
    }
}
