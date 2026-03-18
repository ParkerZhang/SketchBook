"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const index_1 = require("../src/index");
describe('Basic tests', () => {
    test('Create an Agent', () => {
        const agent = new index_1.Agent('TestAgent');
        expect(agent.name).toBe('TestAgent');
        expect(agent.sessions).toHaveLength(0);
    });
    test('Create a Session', () => {
        const session = new index_1.Session('meeting');
        expect(session.type).toBe('meeting');
    });
    test('Call process() returns a string', () => {
        const session = new index_1.Session('test');
        const result = session.process('hello');
        expect(typeof result).toBe('string');
        expect(result).toBe('Processed: hello');
    });
    test('Agent creates and stores sessions', () => {
        const agent = new index_1.Agent('Alice');
        const session = agent.createSession('chat');
        expect(agent.sessions).toHaveLength(1);
        expect(session.type).toBe('chat');
    });
});
//# sourceMappingURL=basic.test.js.map