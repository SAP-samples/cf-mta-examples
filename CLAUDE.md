# cf-mta-examples

Example & reference catalog of ready-to-use MTA projects demonstrating deployment
patterns on SAP BTP Cloud Foundry: app deployment, blue-green updates, service
management, cross-MTA configuration sharing, hooks, and more.

**Security boundary:** This is an OPEN SOURCE repository. Never introduce proprietary
logic, credentials, or internal company context from other workspace repositories
into this codebase.

## Tech Stack

- **MTA descriptors** — `mta.yaml` (development) / `mtad.yaml` (deployment), schema 3.x
- **Java** (Maven, `sap_java_buildpack_jakarta`) — used in blue-green and namespace examples
- **Static content** — most examples ship pre-built `appBits.zip` or `.war` files
- **Cloud MTA Build Tool (`mbt`)** — optional, for building from `mta.yaml`
- **CF CLI v8 + MultiApps CLI Plugin** — required for all deployments

## CLI Commands

```bash
# Build from mta.yaml (when source is present)
mbt build -p cf -t .

# Deploy a directory (uses mtad.yaml)
cf deploy ./ -f

# Deploy an .mtar archive
cf deploy <name>.mtar -f

# Blue-green deployment
cf deploy <name>.mtar -f --strategy blue-green
cf bg-deploy <name>.mtar -f

# Partial deploy (specific modules/resources)
cf deploy -m <module> -r <resource>

# Undeploy
cf undeploy <mta-id> -f --delete-services
```

## Repository Structure

Each top-level directory is a self-contained example with its own `README.adoc`,
MTA descriptor(s), and pre-built artifacts. No shared build system or test suite.

## Capability-to-Example Lookup

| Capability                  | Example directories                                                  |
|-----------------------------|----------------------------------------------------------------------|
| Basic app deploy            | cf-app, cf-app-features, cf-app-docker, app-routes, skip-app-deploy  |
| Service management          | create-managed-services, user-provided-service, active-optional-resources |
| Content deployment          | content-deployment/*                                                 |
| Cross-MTA config            | cross-mta-configurations, cross-mta-config-with-dev-descriptor, dynamic-parameters |
| Environment overrides       | extension-descriptor-different-environments, externalize-services-configurations |
| Zero-downtime (blue-green)  | blue-green-deploy-legacy, blue-green-deploy-strategy/*, idle-parameters |
| Parallel / sequential       | parallel-deployment, deploy-with-sequential-resources                |
| State preservation          | keep-existing-routes, keep-existing-bindings, keep-existing-env      |
| Hooks & lifecycle           | hooks                                                                |
| Namespace isolation         | namespace                                                            |
| Partial / selective deploy  | partial-build-deploy, modify-packaged-mta                           |
| Monitoring & logging        | dynatrace-monitoring, streaming-logs-with-user-provided-service      |
| Service keys & updates      | service-key-renewal, skip-service-updates, fail-on-service-update    |
| Destinations                | cf-destination, cf-service-destination                               |
| Sharing values              | sharing-values-between-apps, parameter-and-property-metadata         |
| Remote deploy               | deploy-with-url                                                      |

## Adding a New Example

1. Create a directory named after the use case (lowercase, hyphens).
2. Include at minimum: `mtad.yaml` (or `mta.yaml` + buildable source), `README.adoc`, any pre-built artifacts.
3. Follow `TEMPLATE_USE_CASE.adoc` for README structure.
4. Add a link entry under the appropriate section in the root `README.adoc`.

## Conventions

- Descriptors use MTA schema **3.x** (`_schema-version: "3.3.0"` or `"3.0.0"`).
- Examples must be self-contained — no cross-directory dependencies.
- Pre-built binaries are checked in so users can deploy without a build step.
- Java examples target **Java 25** with `sap_java_buildpack_jakarta`.