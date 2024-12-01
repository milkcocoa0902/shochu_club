Application Core (アプリケーションのエントリーポイント。Ktorの立ち上げなど)
|
|--> application (アプリケーション層)
|      |
|      |--> controller (presentation/controllerからリクエストを受け、application/usecaseを用いて処理を行う
|      |--> service    (application/usecaseからのリクエストを受け、各種リポジトリを用いて処理を行う)
|      |--> usecase    (application/controllerからのリクエストを受け、application/serviceを用いて処理を行う)
|
|--> domain (ドメイン層)
|      |
|      |--> model      (システムで一貫したモデルを定義する)
|      |--> repository (infraで具象実装するリポジトリの抽象定義をする。)
|      |--> service    (application/serviceで具象実装するサービスの抽象実装をする)
|
|--> infla (インフラ層)
|      |
|      |--> database   (DBとのデータ入出力に関わるRepositoryを実装する)
|      |--> di         (domain/repository, domain/service, domain/usecaseの依存性注入を行う)
|      |--> firebase   (Firebaseとのデータ入出力に関わるRepositoryを実装する)
|
|--> presentation
       |
       |--> controller (application/controllerを用いてクライアントとのデータの入出力を行う。今回は、ここでRPCサービスが具象実装される)
       |--> routing    (presentation/controllerを用いてエンドポイントを定義する)


Shared
|
|--> Protocol (RPCサービスを定義する)
|--> Model    (RPCのエンティティを定義する)
