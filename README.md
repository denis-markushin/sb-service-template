# Service Template

## How to use

### Interactive mode:

 ```bash
 gradle cleanArch generate -i
 ```

### Batch Mode:

```bash
gradle cleanArch generate -i -Dtarget=generated -Dgroup=com.xxx.yyy -Dname=dummy-service -Dversion=1.0.0-SNAPSHOT
```

### Parameters

#### Prompted

The Following parameters will be asked if not available in system properties

| Param   | Description                                         | Default      |
|---------|-----------------------------------------------------|--------------|
| group   | group name in Gradle or Maven, *Mandatory*          |              |
| name    | name in Gradle, of artifactId in Maven, *Mandatory* |              |
| version | version in Gradle or Maven, *Mandatory*             | 1.0-SNAPSHOT |

#### Won't Be Prompted

Following parameters will NOT be prompted, if not available in system properties.

| Param           | Description                                                                                     | Default                        |
|-----------------|-------------------------------------------------------------------------------------------------|--------------------------------|
| templates       | The folder path where template locates, *Mandatory*                                             | `src/main/resources/templates` |
| failIfFileExist | Fail if there are files with the same name exist in the `generated` folder; otherwise overwrite | `true`                         |