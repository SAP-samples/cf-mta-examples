:toc:

# Module hooks

Define and execute hooks at specific phases of module deployment.

You can use hooks to change the typical deployment process, in this case to enable tasks to be executed during a specific moment of the application deployment. For example, you can set hooks can be executed before or after the actual deployment steps for a module, depending on the applications' need.

## Official Documentation
* SAP Help Portal: link:https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/b9245ba90aa14681a416065df8e8c593.html[Module Hooks]

## Activation

To activate the feature:

- Use `_schema-version` with major version `3`, for instance `3.1.0` or `3.3.0` 
- Add hooks section in the module as well as some hooks

```yaml
...
ID: a.cf.app
_schema-version: 3.3.0
version: 0.0.0

modules:
  - name: cf-app-with-hooks
    type: application 
    path: "appBits.zip"
    hooks:
      - name: test-hook
        type: task
        phases:
          - blue-green.application.before-stop.idle
        parameters:
          name: foo-task
          command: "echo Hello"
...
```
## Hooks and extension descriptor
You can also extend module hooks through the extension descriptor. To do so, add the code with your specific parameters.
##### What can be done
- Parameters section can be modified which includes all properties like name, command, memory and disk quota.
##### What cannot be done
- Phases of the hooks cannot be modified via extension descriptors
- Type of the hook cannot be modified
- Required dependencies cannot be modified
- New hooks cannot be added
```yaml
...
ID: a.cf.app
_schema-version: 3.3
extends: foo

modules:
  - name: cf-app-with-hooks
    hooks:
      - name: test-hook-1
        parameters:
          name: foo-task-1
          command: "echo Hello"
...
```

## Try out

In the current directory of the repository, run `cf deploy` that will use `mtad.yaml`. This will automatically assemble an MTA archive and deploy it.
```bash
$cf deploy
Deploying multi-target app archive ~/hooks.mtar in org ****** / space ****** as ******...
...
Stopping application "cf-app-with-hooks-blue"...
Executing hook "test-hook"
Executing task "foo-task" on application "cf-app-with-hooks-blue"...
Starting application "cf-app-with-hooks-blue"...
...
```
