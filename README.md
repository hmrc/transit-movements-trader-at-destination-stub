
# transit-movements-trader-at-destination-stub

The stub data in this repo has been configured to allow us to test the CTC frontend micro-services without the dependency of integrating with the backend.

The test data has been designed in a way that allows us to access the service with data in a predefined state, described below:

## Stub data

> Note - If you have the services running locally pointing at the stub you may use this table interactively.
> http://localhost:9485/manage-transit-movements/


| MRN                | Status                        | Action                                                                                                            | Notes                          |
|--------------------|-------------------------------|-------------------------------------------------------------------------------------------------------------------|--------------------------------|
| 19IT02110010007827 | You can unload                | [Make unloading remarks](http://localhost:9488/common-transit-convention-unloading-arrival/2/unloading-guidance) | With seals                     |
| 99IT9876AB88901209 | Arrival notification rejected | [View and fix errors](http://localhost:9483/common-transit-convention-trader-arrival/3/arrival-rejection)         | Duplicate MRN (Error Code: 91) |
| 18GB0000601001EB15 | You can unload                |                                                                                                                   |                                |
| 99IT9876AB88909999 | UnloadingRemarksSubmitted     | [Make unloading remarks](http://localhost:9488/common-transit-convention-unloading-arrival/5/unloading-guidance)  | Without seals                  |
| 99IT9876AB88912345 | Goods Released                |                                                                                                                   |                                |
| 19IT02110010007A33 | Arrival notification rejected | [View and fix errors](http://localhost:9483/common-transit-convention-trader-arrival/7/arrival-rejection)         | Generic error (Error Code: 12) |
| 19IT02110010007826 | Arrival notification sent     |                                                                                                                   |                                |

## Starting services using stub

The command below starts the `CTC_TRADERS_ARRIVAL` service manager profile, overriding the interface with the backend for the listed micro-services, to use the stub:

- DECLARE_TRANSIT_MOVEMENT_UNLOADING_FRONTEND
- MANAGE_TRANSIT_MOVEMENTS_FRONTEND
- DECLARE_TRANSIT_MOVEMENT_ARRIVAL_FRONTEND


`sm --start CTC_TRADERS_ARRIVAL --appendArgs '{"DECLARE_TRANSIT_MOVEMENT_UNLOADING_FRONTEND":["-Dmicroservice.services.arrivals-backend.port=9481", "-Dmicroservice.services.arrivals-backend.uri=/common-transit-convention-trader-at-destination"], "MANAGE_TRANSIT_MOVEMENTS_FRONTEND":["-Dmicroservice.services.destination.port=9481", "-Dmicroservice.services.destination.startUrl=common-transit-convention-trader-at-destination"], "DECLARE_TRANSIT_MOVEMENT_ARRIVAL_FRONTEND":["-Dmicroservice.services.destination.port=9481","-Dmicroservice.services.destination.startUrl=common-transit-convention-trader-at-destination"]}' -f`
