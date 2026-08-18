export const CLIENTOPT = {
    host: '54.93.73.9',
    password: '8198260375ee1cf041be1575fe12ebe01187f8b7e2e2d55ec0d117420d1ffee3',
    retry_strategy: (options): any => {
        if (options.error.code === 'ECONNREFUSED') {
            // End reconnecting on a specific error and flush all commands with a individual error
            return new Error('The server refused the connection');
        }
        if (options.total_retry_time > 1000 * 60 * 60) {
            // End reconnecting after a specific timeout and flush all commands with a individual error
            return new Error('Retry time exhausted');
        }
        if (options.times_connected > 10) {
            // End reconnecting with built in error
            return undefined;
        }
        // reconnect after
        return Math.max(options.attempt * 100, 3000);
    }
};
export enum TYPE { passenger, provider };
