"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Session = void 0;
class Session {
    constructor(type) {
        this.type = type;
    }
    process(input) {
        return `Processed: ${input}`;
    }
}
exports.Session = Session;
//# sourceMappingURL=Session.js.map