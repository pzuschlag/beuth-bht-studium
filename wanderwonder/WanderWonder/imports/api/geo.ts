import { Mongo } from 'meteor/mongo';

export const Routes = new Mongo.Collection<any>('routes');
export const Points = new Mongo.Collection<any>('points');
