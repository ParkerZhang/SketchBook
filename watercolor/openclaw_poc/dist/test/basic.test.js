"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const src_1 = require("../src");
describe('Basic Test', () => {
    test('should create an Agent', () => {
        const agent = new src_1.Agent('TestAgent');
        expect(agent.name).toBe('TestAgent');
        expect(agent.sessions).toEqual([]);
    });
    test('should create a Session', () => {
        const session = new src_1.Session('test');
        expect(session.type).toBe('test');
    });
    test('should call process and return string', () => {
        const session = new src_1.Session('test');
        const result = session.process('hello');
        expect(typeof result).toBe('string');
        expect(result).toContain('hello');
    });
    test('should create session from Agent', () => {
        const agent = new src_1.Agent('TestAgent');
        const session = agent.createSession('meeting');
        expect(session).toBeInstanceOf(src_1.Session);
        expect(session.type).toBe('meeting');
        expect(agent.sessions).toHaveLength(1);
    });
});
//# sourceMappingURL=basic.test.js.map