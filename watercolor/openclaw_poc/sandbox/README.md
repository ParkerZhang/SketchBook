# Sandbox

This folder contains the sandbox implementation for the multi-agent meeting system.

## Rules
- Sandbox classes extend the real classes in `src/`
- No wrappers
- No modification to `src/`
- All new logic lives here
- AI must not scan or modify `src/` or `test/`

## Files
- `AgentSandbox.ts` - Extends Agent with meeting session support
- `SessionSandbox.ts` - Extends Session with meeting behavior
- `SessionSelector.ts` - Manages session reuse
- `Meeting.ts` - Manages multi-agent meetings
- `SandboxRunner.ts` - Entry point for running sandbox demo