# AttendanceGrouper
A tool for dividing confirmed attendees into equal‑sized groups.

## Requirements
--------------------------------------------------------------------------------

- Java 21

CSV Format
--------------------------------------------------------------------------------

The input CSV file must have the following header and format:

```csv
名前,出席,応答
John Smith <john.smith@example.com>,任意,承諾済み
... (other rows) ...
```

- **名前**: “Full Name <email>” 形式。メールアドレスは必須ではありませんが、入力時に併記できます。出力には表示されません。
- **出席**: 任意の文字列（本アプリでは特に判定に使用しません）。
- **応答**: “承諾済み” の行のみがグループ分け対象となり、それ以外は無視されます。

Build and run using:
--------------------------------------------------------------------------------

```bash
# build and execute in one step
cd AttendanceGroup
./mvnw clean package && java -jar target/AttendanceGroup-1.0-SNAPSHOT.jar <入力CSV> <グループ数>
```

- `<入力CSV>`: グループ分け対象の CSV ファイルパス
- `<グループ数>`: 割り当てたいグループ数（整数）

**Example:**
```bash
cd AttendanceGroup
./mvnw clean package && java -jar target/AttendanceGroup-1.0-SNAPSHOT.jar attendance.csv 4
```
承諾済みの参加者数が指定グループ数で割り切れない場合、エラーで終了します。

## Sample Data
--------------------------------------------------------------------------------

```bash
java -jar AttendanceGroupApp.jar <入力CSV> <グループ数>
```

- `<入力CSV>`: グループ分け対象の CSV ファイルパス
- `<グループ数>`: 割り当てたいグループ数（整数）

実行例:

```bash
java -jar AttendanceGroupApp.jar attendance.csv 4
```

- 承諾済みの参加者数が指定グループ数で割り切れない場合、エラーで終了します。

## Sample Data
--------------------------------------------------------------------------------

以下は動作確認用のサンプル `attendance.csv`（20 名分）です。

```csv
名前,出席,応答
John Smith <john.smith@example.com>,任意,承諾済み
Emily Johnson <emily.johnson@example.com>,任意,承諾済み
Michael Brown <michael.brown@example.com>,任意,承諾済み
Jessica Davis <jessica.davis@example.com>,任意,承諾済み
David Wilson <david.wilson@example.com>,任意,承諾済み
Sarah Miller <sarah.miller@example.com>,任意,承諾済み
Christopher Moore <christopher.moore@example.com>,任意,承諾済み
Laura Taylor <laura.taylor@example.com>,任意,承諾済み
Daniel Anderson <daniel.anderson@example.com>,任意,承諾済み
Rachel Thomas <rachel.thomas@example.com>,任意,承諾済み
Matthew Jackson <matthew.jackson@example.com>,任意,承諾済み
Jennifer White <jennifer.white@example.com>,任意,承諾済み
Joshua Harris <joshua.harris@example.com>,任意,仮の予定
Megan Martin <megan.martin@example.com>,任意,仮の予定
Andrew Thompson <andrew.thompson@example.com>,任意,仮の予定
Brittany Garcia <brittany.garcia@example.com>,任意,仮の予定
Kevin Lee <kevin.lee@example.com>,任意,応答なし
Stephanie Clark <stephanie.clark@example.com>,任意,応答なし
Brian Rodriguez <brian.rodriguez@example.com>,任意,応答なし
Katherine Lewis <katherine.lewis@example.com>,任意,応答なし
```

- 上記サンプルでは「承諾済み」12 名を、たとえば 3 グループまたは 4 グループに分けることができます。

