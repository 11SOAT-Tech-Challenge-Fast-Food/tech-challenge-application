# detekt

## Metrics

* 381 number of properties

* 329 number of functions

* 167 number of classes

* 62 number of packages

* 137 number of kt files

## Complexity Report

* 5,880 lines of code (loc)

* 4,775 source lines of code (sloc)

* 3,027 logical lines of code (lloc)

* 2 comment lines of code (cloc)

* 405 cyclomatic complexity (mcc)

* 38 cognitive complexity

* 11 number of total code smells

* 0% comment source ratio

* 133 mcc per 1,000 lloc

* 3 code smells per 1,000 lloc

## Findings (11)

### complexity, TooManyFunctions (1)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/order/external/api/handler/ErrorHandler.kt:17:7
```
Class 'ErrorHandler' with '12' functions detected. Defined threshold inside classes is set to '12'
```
```kotlin
14 import org.springframework.web.client.HttpClientErrorException
15 
16 @RestControllerAdvice
17 class ErrorHandler {
!!       ^ error
18     @ExceptionHandler(Exception::class)
19     fun handleException(ex: Exception): ResponseEntity<ExceptionResponse> {
20         return buildExceptionResponse(

```

### exceptions, TooGenericExceptionCaught (3)

The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.

[Documentation](https://detekt.dev/docs/rules/exceptions#toogenericexceptioncaught)

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/order/external/jms/receiver/JMSReceiver.kt:21:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
18     fun receiveMessage(event: EventDTO) {
19         try {
20             orderController.updateOrder(event.orderId, event.status)
21         } catch (e: Exception) {
!!                  ^ error
22             logger.error("Erro ao receber mensagem para a fila: orderId={}", event.orderId, e)
23             throw e
24         }

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/core/gateways/PaymentGateway.kt:60:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
57                 externalId = qrCode.externalId,
58                 qrCode = qrCode.qrCode
59             )
60         } catch (e: Exception) {
!!                  ^ error
61             throw ProviderException("Error while generating payment with provider")
62         }
63     }

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/external/jms/sender/JMSSender.kt:27:18
```
The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled.
```
```kotlin
24                 message.setStringProperty("content-type", "application/json")
25                 message
26             }
27         } catch (e: Exception) {
!!                  ^ error
28             logger.error("Erro ao enviar mensagem para a fila: orderId={}", orderId, e)
29             throw e
30         }

```

### naming, EnumNaming (7)

Enum names should follow the naming convention set in the projects configuration.

[Documentation](https://detekt.dev/docs/rules/naming#enumnaming)

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:6:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
3  import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
4  
5  enum class PaymentProviderStatus {
6      created,
!      ^ error
7      processed,
8      action_required,
9      failed,

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:7:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
4  
5  enum class PaymentProviderStatus {
6      created,
7      processed,
!      ^ error
8      action_required,
9      failed,
10     processing,

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:8:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
5  enum class PaymentProviderStatus {
6      created,
7      processed,
8      action_required,
!      ^ error
9      failed,
10     processing,
11     refunded,

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:9:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
6      created,
7      processed,
8      action_required,
9      failed,
!      ^ error
10     processing,
11     refunded,
12     canceled;

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:10:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
7      processed,
8      action_required,
9      failed,
10     processing,
!!     ^ error
11     refunded,
12     canceled;
13 

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:11:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
8      action_required,
9      failed,
10     processing,
11     refunded,
!!     ^ error
12     canceled;
13 
14     fun toEntityStatus(): PaymentStatus {

```

* /Users/biancavediner/Downloads/pos/order-management/src/main/kotlin/br/com/fiap/ordermanagement/payment/common/PaymentProviderStatus.kt:12:5
```
Enum entry names should match the pattern: [A-Z][_a-zA-Z0-9]*
```
```kotlin
9      failed,
10     processing,
11     refunded,
12     canceled;
!!     ^ error
13 
14     fun toEntityStatus(): PaymentStatus {
15         return when (this) {

```

generated with [detekt version 1.23.4](https://detekt.dev/) on 2025-07-30 23:11:54 UTC
