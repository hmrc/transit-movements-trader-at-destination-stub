
# Transit movements: Trader at destination stub

The stub data in this repository has been configured to allow us to test the Common Transit Convention (CTC)Traders front-end micro-services without having to integrate with an NCTS instance.

We have designed the test data to allow us to access the service with data in a predefined state which is described below:

## Stub data

> Note - If you have the services running locally pointing at the stub, you can use this table interactively.
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

This command starts the service, through service manager: `sm --start TRANSIT_MOVEMENTS_TRADER_AT_DESTINATION_STUB`

For more details of how to [start the wholestack for the front-end](https://github.com/hmrc/manage-transit-movements-frontend).

If you want to start the [whole stack for the API](https://github.com/hmrc/common-transit-convention-traders).

## Helpful information

Guides for the related public Common Transit Convention Traders API are on the [HMRC Developer Hub](https://developer.service.hmrc.gov.uk/api-documentation/docs/using-the-hub)

## Reporting Issues

If you have any issues relating to the Common Transit Convention Traders API, please raise them through our [public API](https://github.com/hmrc/common-transit-convention-traders#reporting-issues).

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

