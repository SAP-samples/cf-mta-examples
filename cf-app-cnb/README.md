# Staging a CF app with the Cloud Native Buildpacks lifecycle
This example shows how to model a Cloud Foundry application whose source code is staged on the platform with the [Cloud Native Buildpacks (CNB) lifecycle][cf-cnb-lifecycle], rather than the classic "buildpack v2" lifecycle. The CNB lifecycle lets you reference any [CNB-compliant builder image][buildpacks-io] (typically from [Paketo Buildpacks][paketo-io] or your own registry) and have the platform run it during staging. CNBs are considered to be the future for Cloud Foundry and already bring in [new capabilities](<https://github.com/cloudfoundry/community/blob/main/toc/rfc/rfc-0017-add-cnbs.md>), including SBOMs, different compilation-options for traditionally supported languages, such as native-builds for Java and support for languages that have been uncovered so far (e.g. Rust, Elixir, Haskell).

The bundled application is a verbatim copy of the [`nodejs/npm` sample][upstream-sample] from the upstream `paketo-buildpacks/samples` repository: a small Express HTTP server. Only [`mta.yaml`](<mta.yaml>), [`mtad.yaml`](<mtad.yaml>) and a [`.cfignore`](<.cfignore>) are added on top to wrap the sample as an MTA module.

[cf-cnb-lifecycle]: <https://v3-apidocs.cloudfoundry.org/#the-cnb-lifecycle>
[buildpacks-io]: <https://buildpacks.io>
[paketo-io]: <https://paketo.io>
[upstream-sample]: <https://github.com/paketo-buildpacks/samples/tree/main/nodejs/npm>

## Try it out
The example demonstrates 2 different approaches that lead to the same result.

### Deploy directly from directory
This approach uses deployment descriptor [`mtad.yaml`](<mtad.yaml>). The `.cfignore` file makes sure that no local development artefacts (e.g. `node_modules/` produced by a local `npm install`) are uploaded:
```shell
$ cf deploy ./ -f
```

### Build an MTA archive and deploy it
This approach uses development descriptor [`mta.yaml`](<mta.yaml>) to package the sources into an MTAR archive:
```shell
$ mbt build --platform=cf --target=.
$ cf deploy a.cnb.cf.app_0.0.0.mtar -f
```

The Paketo Node.js buildpack then runs `npm install` during staging on the platform and registers `node server.js` (taken from the `start` script in `package.json`) as the `web` process type, so no explicit `command:` parameter is required in the descriptors.

## Examine the result
```shell
$ cf mta a.cnb.cf.app
Showing health and status for multi-target app a.cnb.cf.app...
OK
Version: 0.0.0

Apps:
name                  requested state   instances   memory   disk   urls
my-cnb-managed-app    started           1/1         256M     512M   <host>.<domain>

$ cf app my-cnb-managed-app
...
buildpacks:
        - docker://docker.io/paketobuildpacks/nodejs

$ curl https://<host>.<domain>/actuator/health
{"status":"UP"}
```

## Clean up resources
```shell
$ cf undeploy a.cnb.cf.app -f
```
