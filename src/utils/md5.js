import crypto from 'crypto-js';
export function md5(info) {
    return crypto.MD5(info).toString()
}